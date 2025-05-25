package com.mialeds.models.dtos.UsuarioController;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CambiarClaveDTO {

    @NotBlank(message = "La contraseña antigua es obligatoria")
    private String claveAntigua;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String claveNueva1;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String claveNueva2;

    public CambiarClaveDTO() {
    }

    public CambiarClaveDTO(String claveAntigua, String claveNueva1, String claveNueva2) {
        this.claveAntigua = claveAntigua;
        this.claveNueva1 = claveNueva1;
        this.claveNueva2 = claveNueva2;
    }
    
    public String getClaveAntigua() {
        return claveAntigua;
    }

    public void setClaveAntigua(String claveAntigua) {
        this.claveAntigua = claveAntigua;
    }

    public String getClaveNueva1() {
        return claveNueva1;
    }

    public void setClaveNueva1(String claveNueva1) {
        this.claveNueva1 = claveNueva1;
    }

    public String getClaveNueva2() {
        return claveNueva2;
    }

    public void setClaveNueva2(String claveNueva2) {
        this.claveNueva2 = claveNueva2;
    }
}
