package com.mialeds.models.dtos.VentaController;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VentaEditarDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser mayor a 0")
    private Integer idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer total;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    public VentaEditarDTO() {
    }

    public VentaEditarDTO(Integer idProducto, Integer cantidad, Integer total, LocalDate fecha) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
    }
    
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}