package com.cross.procrop.controllers;

import com.cross.procrop.services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*") // Allows API calls from the frontend
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/scan-disease")
    public ResponseEntity<Map<String, Object>> scanDisease(@RequestParam("image") MultipartFile image) {
        // Here we would typically save the file or send its stream to a Python microservice.
        // For demonstration, we use our mocked AIService.
        return ResponseEntity.ok(aiService.scanImageForDisease(image));
    }

    @PostMapping("/chatbot")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.getOrDefault("message", "");
        return ResponseEntity.ok(aiService.getChatbotResponse(message));
    }
}
