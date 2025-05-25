package com.mialeds.models.dtos.UsuarioController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioEditarDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo electrónico inválido")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    public UsuarioEditarDTO() {
    }

    public UsuarioEditarDTO(String nombre, String cedula, String correo, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}