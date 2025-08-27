package main.java.com.aichat.service;

import com.aichat.model.ChatMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    @Value("${openai.api.url}")
    private String apiUrl;
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    public String getChatResponse(List<ChatMessage> messages) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);
        
        // Set headers
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        
        // Build request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        
        JSONArray messagesArray = new JSONArray();
        for (ChatMessage message : messages) {
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", message.getRole());
            messageObj.put("content", message.getContent());
            messagesArray.put(messageObj);
        }
        requestBody.put("messages", messagesArray);
        
        httpPost.setEntity(new StringEntity(requestBody.toString()));
        
        // Execute request
        CloseableHttpResponse response = client.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity());
        
        // Parse response
        JSONObject responseJson = new JSONObject(responseString);
        JSONArray choices = responseJson.getJSONArray("choices");
        if (choices.length() > 0) {
            return choices.getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
        
        throw new RuntimeException("No response from OpenAI");
    }
}