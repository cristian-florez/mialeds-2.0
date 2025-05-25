package com.mialeds.models.dtos.UsuarioController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RecuperarClaveDTO {
    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo electrónico inválido")
    private String correo;

    public RecuperarClaveDTO() {
    }

    public RecuperarClaveDTO(String cedula, String correo) {
        this.cedula = cedula;
        this.correo = correo;
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
}
