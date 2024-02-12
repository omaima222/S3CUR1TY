package com.example.securityproject.controllers;


import com.example.securityproject.dto.user.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/welcome")
public class WelcomeController {
    @GetMapping("")
    public ResponseEntity<MessageDto> welcome(){
        return ResponseEntity.ok(new MessageDto("Welcome to the app authenticated user :D"));
    }
}
