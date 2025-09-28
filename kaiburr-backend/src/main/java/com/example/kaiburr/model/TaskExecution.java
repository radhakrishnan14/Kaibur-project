package com.example.kaiburr.model;

import java.time.Instant;

public class TaskExecution {
    private Instant startTime;
    private Instant endTime;
    private String output;
    private Integer exitCode;

    public TaskExecution() {}

    public TaskExecution(Instant startTime, Instant endTime, String output, Integer exitCode) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.output = output;
        this.exitCode = exitCode;
    }

    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }
    public Instant getEndTime() { return endTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    public Integer getExitCode() { return exitCode; }
    public void setExitCode(Integer exitCode) { this.exitCode = exitCode; }
}
