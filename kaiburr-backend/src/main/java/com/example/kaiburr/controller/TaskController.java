package com.example.kaiburr.controller;

import com.example.kaiburr.model.Task;
import com.example.kaiburr.model.TaskExecution;
import com.example.kaiburr.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173") // adjust for your frontend
public class TaskController {

    private final TaskService svc;

    public TaskController(TaskService svc) {
        this.svc = svc;
    }

    // List all tasks
    @GetMapping
    public ResponseEntity<List<Task>> listAll() {
        return ResponseEntity.ok(svc.listAll());
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable("id") String id) {
        return svc.findById(id)
                  .map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Search tasks by name
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(svc.searchByName(name));
    }

    // Create or update task
    @PutMapping
    public ResponseEntity<?> putTask(@Valid @RequestBody Task task) {
        try {
            Task saved = svc.save(task);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Execute task by ID
    @PutMapping("/{id}/execute")
    public ResponseEntity<?> execute(@PathVariable("id") String id) {
        try {
            TaskExecution exec = svc.executeTask(id);
            return ResponseEntity.ok(exec);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Execution failed: " + e.getMessage());
        }
    }
}
