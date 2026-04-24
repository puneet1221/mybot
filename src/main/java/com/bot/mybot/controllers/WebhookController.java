package com.bot.mybot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bot.mybot.dto.Update;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @PostMapping
    public ResponseEntity<?> onReceiveUpdate(@RequestBody Update update) {
        long userId = update.getUserId();
        String messageText = update.getMessage();
        log.info("webhook update received from user {} and message {}", userId, messageText);
        return handleUpdate(update);
    }

    private ResponseEntity<?> handleUpdate(Update update) {
        String messageText = update.getMessage();
        switch (messageText) {
            case ("/start") -> {
                return ResponseEntity.status(HttpStatus.OK).body(
                        String.format("""
                                Hello %s welcome ! How can I assist you
                                """, update.getFirstName())

                );
            }
            case "Hi" -> {
                return ResponseEntity.status(HttpStatus.OK).body("Hello " + update.getFirstName());
            }
            case "Bye" -> {
                return ResponseEntity.status(HttpStatus.OK).body("Goodbye! " + update.getFirstName());
            }
            default -> {
                return ResponseEntity.status(HttpStatus.OK).body("I didn't understood");
            }
        }
    }

}
