package com.mialeds.models.dtos.ProveedorController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ProveedorDTO {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    public ProveedorDTO() {
    }

    public ProveedorDTO(String nombre, String nit, String correo, String telefono) {
        this.nombre = nombre;
        this.nit = nit;
        this.correo = correo;
        this.telefono = telefono;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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