package com.mialeds.controllers.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.services.EmailService;

@RestController
@RequestMapping("/email")
public class emailController {

    @Autowired
    private EmailService emailService;

    //metodo para enviar un correo electronico
    @PostMapping("/restaurarClave")
    public Map<String, String> restaurarClave(@RequestBody Map<String, String> data){
        boolean respuesta = emailService.enviarCorreo(                data.get("cedula_olvide_clave").toString(),
        data.get("correo_olvide_clave").toString());

        if (respuesta) {
            return Map.of("mensaje", "Correo de recuperación enviado con éxito. Por favor revise su correo electrónico.");
        } else {
            return Map.of("mensaje", "Error en las credenciales.");
        }
    }
}
