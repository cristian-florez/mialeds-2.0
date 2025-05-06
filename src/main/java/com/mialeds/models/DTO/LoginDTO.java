package com.mialeds.models.DTO;

public class LoginDTO {

    private String cedula;
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
