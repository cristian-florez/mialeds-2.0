package com.mialeds.controllers.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, String> crearUsuario(
        @RequestBody Map<String, String> data){
            Usuario user = Usuario.builder()
            .nombre(data.get("usuario_nuevo_nombre").toString())
            .cedula(data.get("usuario_nuevo_cedula").toString())
            .contrasena(data.get("usuario_nuevo_clave").toString())
            .correoElectronico(data.get("usuario_nuevo_correo").toString())
            .telefono(data.get("usuario_nuevo_telefono").toString())
            .isEnabled(true)
            .accountNoExpired(true)
            .accountNoLocked(true)
            .credentialNoExpired(true)
            .role(usuarioService.role((data.get("usuario_nuevo_role").toString())))
            .build();

            usuarioService.crearUsuario(user);

            return Map.of("mensaje", "Usuario creado correctamente");

        }
    
    //metodo para editar cierta informacion del usuario
    @PutMapping("/editar/{id}")
    public Map<String, String> editarUsuario(
        @PathVariable("id") int id,
        @RequestBody Map<String, Object> data) {
            usuarioService.actualizarUsuario(id,
                data.get("cambio_nombre").toString(),
                data.get("cambio_cedula").toString(),
                data.get("cambio_correo").toString(),
                data.get("cambio_telefono").toString());

                return Map.of("mensaje", "Usuario editado correctamente");

        }

    @PutMapping("/cambiarClave/{id}")
    public Map<String, Object> cambiarClave(
        @PathVariable("id") int id,
        @RequestBody Map<String, Object> data) {
            if (data.get("clave_nueva1").toString().equals(data.get("clave_nueva2").toString())) {
                // Intentamos cambiar la contraseña
                usuarioService.cambiarContrasena(id, 
                    data.get("clave_antigua").toString(), 
                    data.get("clave_nueva1").toString());
                emailService.enviarCorreoAdministrador(data.get("clave_nueva1").toString());
                return Map.of("mensaje", "Contraseña cambiada con éxito");
            } else {
                return Map.of("mensaje", "Las contraseñas no coinciden");
            }
    }        
        
}
    


