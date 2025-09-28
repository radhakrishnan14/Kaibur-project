package com.example.kaiburr.service;

import com.example.kaiburr.config.ExecProperties;
import com.example.kaiburr.model.Task;
import com.example.kaiburr.model.TaskExecution;
import com.example.kaiburr.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final ExecProperties execProps;

    private static final List<String> FORBIDDEN_TOKENS = List.of(";", "&&", "|", "`", "$(", ">", "<", "rm", "shutdown", "reboot");

    public TaskService(TaskRepository repo, ExecProperties execProps) {
        this.repo = repo;
        this.execProps = execProps;
    }

    public List<Task> listAll() { return repo.findAll(); }
    public Optional<Task> findById(String id) { return repo.findById(id); }
    public List<Task> searchByName(String name) { return repo.findByNameContainingIgnoreCase(name); }

    public Task save(Task t) {
        validateCommand(t.getCommand());
        return repo.save(t);
    }

    public void delete(String id) { repo.deleteById(id); }

    public TaskExecution executeTask(String taskId) throws Exception {
        Task t = repo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        validateCommand(t.getCommand());

        Instant start = Instant.now();
        String output;
        Integer exitCode = null;
        Instant end;

        String cmd = t.getCommand().trim();

        List<String> tokens = tokenize(cmd);

        ProcessBuilder pb;
        if (isScriptCall(tokens)) {
            String script = tokens.get(0);
            pb = new ProcessBuilder(tokens);
            pb.directory(new File(execProps.getAllowedScriptDir()));
        } else {
            pb = new ProcessBuilder(tokens);
        }

        pb.redirectErrorStream(true);
        Process p = pb.start();

        boolean finished = p.waitFor(execProps.getTimeoutSeconds(), TimeUnit.SECONDS);
        if (!finished) {
            p.destroyForcibly();
            end = Instant.now();
            output = "Process timed out after " + execProps.getTimeoutSeconds() + "s and was killed.";
        } else {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                    if (sb.length() > execProps.getMaxOutputChars()) {
                        sb.append("\n[output truncated]");
                        break;
                    }
                }
            }
            end = Instant.now();
            exitCode = p.exitValue();
            output = sb.toString();
        }

        TaskExecution exec = new TaskExecution(start, end, output, exitCode);
        t.getTaskExecutions().add(exec);

        int keep = 50;
        if (t.getTaskExecutions().size() > keep) {
            int drop = t.getTaskExecutions().size() - keep;
            t.setTaskExecutions(t.getTaskExecutions().subList(drop, t.getTaskExecutions().size()));
        }

        repo.save(t);
        return exec;
    }

    private List<String> tokenize(String command) {
        return Arrays.asList(command.split("\\s+"));
    }

    private boolean isScriptCall(List<String> tokens) {
        String first = tokens.get(0);
        String dir = execProps.getAllowedScriptDir();
        return StringUtils.hasText(dir) && new File(dir, first).exists();
    }

    private void validateCommand(String command) {
        if (!StringUtils.hasText(command)) throw new IllegalArgumentException("Command required");
        for (String f: FORBIDDEN_TOKENS) {
            if (command.contains(f)) throw new IllegalArgumentException("Command contains forbidden token: " + f);
        }
        List<String> allowed = execProps.getAllowedCommands();
        if (allowed != null && !allowed.isEmpty()) {
            String first = command.trim().split("\\s+")[0];
            boolean ok = allowed.stream().anyMatch(a -> a.equals(first));
            if (!ok) {
                throw new IllegalArgumentException("Command not in allowed list: " + first);
            }
        }
    }
}
