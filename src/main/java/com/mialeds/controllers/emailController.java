package com.mialeds.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mialeds.services.EmailService;

@RestController
@RequestMapping("/email")
public class emailController {

    @Autowired
    private EmailService emailService;

    // Método para enviar un correo electrónico de recuperación
    @PostMapping("/restaurarClave")
    public ResponseEntity<Map<String, String>> restaurarClave(@RequestBody Map<String, String> data) {
        boolean respuesta = emailService.enviarCorreo(
            data.get("cedula_olvide_clave").toString(),
            data.get("correo_olvide_clave").toString()
        );

        if (respuesta) {
            return ResponseEntity.ok(
                Map.of("mensaje", "Correo de recuperación enviado con éxito. Por favor revise su correo electrónico.")
            );
        } else {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Error en las credenciales."));
        }
    }
}
