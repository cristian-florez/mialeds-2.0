package com.mialeds.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Usuario;
import com.mialeds.models.dtos.UsuarioController.CambiarClaveDTO;
import com.mialeds.models.dtos.UsuarioController.RecuperarClaveDTO;
import com.mialeds.models.dtos.UsuarioController.UsuarioDTO;
import com.mialeds.models.dtos.UsuarioController.UsuarioEditarDTO;
import com.mialeds.services.EmailService;
import com.mialeds.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario user = Usuario.builder()
            .nombre(usuarioDTO.getNombre())
            .cedula(usuarioDTO.getCedula())
            .contrasena(usuarioDTO.getContrasena())
            .correoElectronico(usuarioDTO.getCorreoElectronico())
            .telefono(usuarioDTO.getTelefono())
            .isEnabled(true)
            .accountNoExpired(true)
            .accountNoLocked(true)
            .credentialNoExpired(true)
            .role(usuarioService.role(usuarioDTO.getRole()))
            .build();

        usuarioService.crearUsuario(user);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario creado correctamente"));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarUsuario(
        @PathVariable("id") int id,@Valid
        @RequestBody UsuarioEditarDTO usuarioEditarDTO) {

        usuarioService.actualizarUsuario(id,
            usuarioEditarDTO.getNombre(),
            usuarioEditarDTO.getCedula(),
            usuarioEditarDTO.getCorreo(),
            usuarioEditarDTO.getTelefono());

        return ResponseEntity.ok(Map.of("mensaje", "Usuario editado correctamente"));
    }

    // Método para enviar un correo electrónico de recuperación
    @PostMapping("/restaurar-clave")
    public ResponseEntity<Map<String, String>> restaurarClave(@Valid @RequestBody RecuperarClaveDTO recuperarClaveDTO) {
        boolean respuesta = emailService.enviarCorreo(
            recuperarClaveDTO.getCedula(),
            recuperarClaveDTO.getCorreo()
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

    @PutMapping("/cambiar-clave/{id}")
    public ResponseEntity<Map<String, String>> cambiarClave(
        @PathVariable("id") int id,@Valid
        @RequestBody CambiarClaveDTO cambiarClaveDTO) {

        String claveNueva1 = cambiarClaveDTO.getClaveNueva1();
        String claveNueva2 = cambiarClaveDTO.getClaveNueva2();

        if (!claveNueva1.equals(claveNueva2)) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Las contraseñas no coinciden"));
        }

        usuarioService.cambiarContrasena(id,
            cambiarClaveDTO.getClaveAntigua(),
            claveNueva1);

        emailService.enviarCorreoAdministrador(claveNueva1);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña cambiada con éxito"));
    }
}
