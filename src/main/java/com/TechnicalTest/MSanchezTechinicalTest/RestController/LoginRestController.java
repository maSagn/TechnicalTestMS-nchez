/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TechnicalTest.MSanchezTechinicalTest.RestController;

import com.TechnicalTest.MSanchezTechinicalTest.Component.JwtUtil;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.CustomerDetailsService;
import com.TechnicalTest.MSanchezTechinicalTest.Service.EncryptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class LoginRestController {
    
    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;

    public LoginRestController(CustomerDetailsService customerDetailsService, JwtUtil jwtUtil) {
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User loginRequest) {
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
