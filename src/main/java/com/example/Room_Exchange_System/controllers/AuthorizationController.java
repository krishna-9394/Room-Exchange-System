package com.example.Room_Exchange_System.controllers;

import com.example.Room_Exchange_System.domain.DTO.AuthorizationDTO;
import com.example.Room_Exchange_System.services.AuthService;
import com.example.Room_Exchange_System.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorizationController {
    private final AuthService authService;
    private final EmailService emailService;
    public AuthorizationController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthorizationDTO newUser) {
        return authService.createUser(newUser);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthorizationDTO user) {
        return authService.login(user);
    }

    @DeleteMapping("/auth/{user}")
    public void deleteUser(@PathVariable("user") String user) {
        authService.deleteUser(user);
    }

    @PostMapping("/sendTestMail")
    public void sendTestEmail() {
        emailService.sendSimpleMessage("ekank1410@gmail.com","Test","this is test email");
    }

}
