package com.example.kaiburr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.exec")
public class ExecProperties {
    private List<String> allowedCommands;
    private String allowedScriptDir;
    private int timeoutSeconds = 30;
    private int maxOutputChars = 20000;

    public List<String> getAllowedCommands() { return allowedCommands; }
    public void setAllowedCommands(List<String> allowedCommands) { this.allowedCommands = allowedCommands; }
    public String getAllowedScriptDir() { return allowedScriptDir; }
    public void setAllowedScriptDir(String allowedScriptDir) { this.allowedScriptDir = allowedScriptDir; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
    public void setTimeoutSeconds(int timeoutSeconds) { this.timeoutSeconds = timeoutSeconds; }
    public int getMaxOutputChars() { return maxOutputChars; }
    public void setMaxOutputChars(int maxOutputChars) { this.maxOutputChars = maxOutputChars; }
}
