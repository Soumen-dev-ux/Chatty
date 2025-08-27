package main.java.com.aichat.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private String role;
    private String content;
    private LocalDateTime timestamp;
    
    // Constructors
    public ChatMessage() {}
    
    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
