package com.mialeds.models.dtos.ProductoController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ProductoMovimientoDTO {

    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private int cantidad;

    @NotBlank(message = "El tipo de movimiento es requerido (entrada o salida)")
    private String movimiento;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    public ProductoMovimientoDTO() {
    }

    public ProductoMovimientoDTO(int cantidad, String movimiento, LocalDate fecha) {
        this.cantidad = cantidad;
        this.movimiento = movimiento;
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
