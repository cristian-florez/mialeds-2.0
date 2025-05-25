package com.mialeds.models.dtos.ProductoController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductoDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La presentación no puede estar vacía")
    private String presentacion;

    @Min(value = 0, message = "El precio de compra debe ser mayor o igual a 0")
    private int precioCompra;

    @Min(value = 0, message = "El precio de venta debe ser mayor o igual a 0")
    private int precioVenta;

    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private int cantidad;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, String presentacion, int precioCompra, int precioVenta, int cantidad) {
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
