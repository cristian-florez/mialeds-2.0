package com.mialeds.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Usuario;
import com.mialeds.services.EmailService;
import com.mialeds.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearUsuario(@RequestBody Map<String, String> data) {
        Usuario user = Usuario.builder()
            .nombre(data.get("usuario_nuevo_nombre"))
            .cedula(data.get("usuario_nuevo_cedula"))
            .contrasena(data.get("usuario_nuevo_clave"))
            .correoElectronico(data.get("usuario_nuevo_correo"))
            .telefono(data.get("usuario_nuevo_telefono"))
            .isEnabled(true)
            .accountNoExpired(true)
            .accountNoLocked(true)
            .credentialNoExpired(true)
            .role(usuarioService.role(data.get("usuario_nuevo_role")))
            .build();

        usuarioService.crearUsuario(user);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario creado correctamente"));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarUsuario(
        @PathVariable("id") int id,
        @RequestBody Map<String, Object> data) {

        usuarioService.actualizarUsuario(id,
            data.get("cambio_nombre").toString(),
            data.get("cambio_cedula").toString(),
            data.get("cambio_correo").toString(),
            data.get("cambio_telefono").toString());

        return ResponseEntity.ok(Map.of("mensaje", "Usuario editado correctamente"));
    }

    @PutMapping("/cambiarClave/{id}")
    public ResponseEntity<Map<String, String>> cambiarClave(
        @PathVariable("id") int id,
        @RequestBody Map<String, Object> data) {

        String claveNueva1 = data.get("clave_nueva1").toString();
        String claveNueva2 = data.get("clave_nueva2").toString();

        if (!claveNueva1.equals(claveNueva2)) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Las contraseñas no coinciden"));
        }

        usuarioService.cambiarContrasena(id,
            data.get("clave_antigua").toString(),
            claveNueva1);

        emailService.enviarCorreoAdministrador(claveNueva1);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña cambiada con éxito"));
    }
}
