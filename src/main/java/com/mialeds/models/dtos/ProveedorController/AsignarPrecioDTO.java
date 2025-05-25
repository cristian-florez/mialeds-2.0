package com.mialeds.models.dtos.ProveedorController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AsignarPrecioDTO {
    
    @NotNull(message = "El ID del proveedor es obligatorio")
    @Min(value = 1, message = "El ID del proveedor debe ser mayor a 0")
    private Integer idProveedor;

    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser mayor a 0")
    private Integer idProducto;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    public AsignarPrecioDTO() {
    }

    public AsignarPrecioDTO(Integer idProveedor, Integer idProducto, Integer precio) {
        this.idProveedor = idProveedor;
        this.idProducto = idProducto;
        this.precio = precio;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}