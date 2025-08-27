package main.java.com.aichat.controller;

import com.aichat.model.ChatMessage;
import com.aichat.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
    
    @Autowired
    private OpenAIService openAIService;
    
    private List<ChatMessage> conversationHistory = new ArrayList<>();
    
    @PostMapping("/message")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage userMessage) {
        try {
            // Add user message to history
            conversationHistory.add(userMessage);
            
            // Get AI response
            String aiResponse = openAIService.getChatResponse(conversationHistory);
            ChatMessage aiMessage = new ChatMessage("assistant", aiResponse);
            
            // Add AI response to history
            conversationHistory.add(aiMessage);
            
            return ResponseEntity.ok(aiMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                new ChatMessage("assistant", "Sorry, I encountered an error: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory() {
        return ResponseEntity.ok(conversationHistory);
    }
    
    @DeleteMapping("/history")
    public ResponseEntity<Void> clearChatHistory() {
        conversationHistory.clear();
        return ResponseEntity.ok().build();
    }
}
