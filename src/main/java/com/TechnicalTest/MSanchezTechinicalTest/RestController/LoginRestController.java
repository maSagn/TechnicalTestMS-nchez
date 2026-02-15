/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TechnicalTest.MSanchezTechinicalTest.RestController;

import com.TechnicalTest.MSanchezTechinicalTest.Component.JwtUtil;
import com.TechnicalTest.MSanchezTechinicalTest.DTO.Login;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.CustomerDetailsService;
import com.TechnicalTest.MSanchezTechinicalTest.Service.EncryptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login RestController", description = "Controller focused on login methods")
@RestController
@RequestMapping("auth")
public class LoginRestController {
    
    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;

    public LoginRestController(CustomerDetailsService customerDetailsService, JwtUtil jwtUtil) {
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }
    
    @Operation(summary = "Endpoint for user authentication", description = "Endpoint for user authentication where tax_id is the username")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Login loginRequest) {
        try {
            User user = customerDetailsService.findByTaxId(loginRequest.getTax_id());
            if (user == null) return ResponseEntity.status(401).body("Usuario no encontrado");

            if (EncryptService.decrypt(user.getPassword()).equals(loginRequest.getPassword())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            String token = jwtUtil.generateToken(user.getTax_id());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
}
