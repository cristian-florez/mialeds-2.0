package com.mialeds.models.dtos.AuthController;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contraseña;
    
    public LoginDTO() {
    }

    public LoginDTO(String cedula, String contraseña) {
        this.cedula = cedula;
        this.contraseña = contraseña;
    }

    public String getCedula() {
        return cedula;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
