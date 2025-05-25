package com.mialeds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Proveedor;
import com.mialeds.models.ProveedorProducto;
import com.mialeds.models.dtos.ProveedorController.AsignarPrecioDTO;
import com.mialeds.models.dtos.ProveedorController.ProveedorDTO;
import com.mialeds.services.ProveedorProductoService;
import com.mialeds.services.ProveedorService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorProductoService proveedorProductoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.listar();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/agrupar-productos")
    public ResponseEntity<Map<String, List<ProveedorProducto>>> listarProductos() {
        Map<String, List<ProveedorProducto>> productos = proveedorProductoService.obtenerProductosAgrupados();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, List<ProveedorProducto>>> buscarProducto(@RequestParam("producto") String nombre) {
        Map<String, List<ProveedorProducto>> resultado = proveedorProductoService.listarPornombre(nombre);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarProveedor(
            @PathVariable("id") int id,@Valid
            @RequestBody ProveedorDTO proveedorDTO) {
        proveedorService.actualizar(
            id,
            proveedorDTO.getNombre(),
            proveedorDTO.getNit(),
            proveedorDTO.getCorreo(),
            proveedorDTO.getTelefono());

        return ResponseEntity.ok(Map.of("mensaje", "Proveedor editado correctamente"));
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
        try {
            proveedorService.crear(
                proveedorDTO.getNombre(),
                proveedorDTO.getNit(),
                proveedorDTO.getCorreo(),
                proveedorDTO.getTelefono()
            );

            return ResponseEntity.ok(Map.of("mensaje", "Proveedor creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al crear el proveedor"));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarProveedor(@PathVariable("id") int id) {
        try {
            proveedorService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("mensaje", "Error al eliminar el proveedor"));
        }
    }

    @PutMapping("/asignar-precio")
    public ResponseEntity<Map<String, String>> asignarPrecio(@Valid @RequestBody AsignarPrecioDTO asignarPrecioDTO) {
        try {

            boolean respuesta = proveedorProductoService.asignarPrecio(
                asignarPrecioDTO.getIdProveedor(),
                asignarPrecioDTO.getIdProducto(),
                asignarPrecioDTO.getPrecio()
            );

            if (respuesta) {
                return ResponseEntity.ok(Map.of("mensaje", "Precio asignado correctamente"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al asignar el precio: producto no encontrado"));
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos"));
        }
    }
}
