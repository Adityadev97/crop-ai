package com.cross.procrop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AIService {

    private final Random random = new Random();
    private final String[] possibleDiseases = {"Leaf Blight", "Rust", "Healthy", "Powdery Mildew", "Bacterial Spot"};
    
    // Mock image scan
    public Map<String, Object> scanImageForDisease(MultipartFile image) {
        Map<String, Object> result = new HashMap<>();
        try {
            // Simulate processing delay
            Thread.sleep(1500);
            
            // Mock response
            String detected = possibleDiseases[random.nextInt(possibleDiseases.length)];
            result.put("disease", detected);
            result.put("confidence", 80.0 + (random.nextDouble() * 19.9)); // 80 to 99.9%
            
            if ("Healthy".equals(detected)) {
                result.put("recommendation", "Crop is healthy. Maintain regular watering schedule.");
            } else {
                result.put("recommendation", "Apply Copper Fungicide or specific organic remedy for " + detected + ".");
            }
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            result.put("error", "Processing interrupted");
            return result;
        }
    }
    
    // Mock Hindi Chatbot response
    public Map<String, String> getChatbotResponse(String message) {
        Map<String, String> response = new HashMap<>();
        String reply = "नमस्ते! मैं आपकी कैसे मदद कर सकता हूँ? (Hello! How can I help you?)";
        
        if (message.toLowerCase().contains("weather") || message.toLowerCase().contains("मौसम")) {
            reply = "आज मौसम साफ रहेगा और बारिश की कोई संभावना नहीं है। (The weather will be clear today with no chance of rain.)";
        } else if (message.toLowerCase().contains("disease") || message.toLowerCase().contains("बीमारी")) {
            reply = "कृपया अपनी फसल की फोटो अपलोड करें ताकि मैं जांच कर सकूं। (Please upload a photo of your crop so I can check.)";
        }
        
        response.put("reply", reply);
        return response;
    }
}
