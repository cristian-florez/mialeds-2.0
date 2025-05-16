package com.mialeds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Proveedor;
import com.mialeds.models.ProveedorProducto;
import com.mialeds.services.ProveedorProductoService;
import com.mialeds.services.ProveedorService;

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
            @PathVariable("id") int id,
            @RequestBody Map<String, Object> data) {

        proveedorService.actualizar(
            id,
            data.get("editar_nombre_proveedor").toString(),
            data.get("editar_nit_proveedor").toString(),
            data.get("editar_correo_proveedor").toString(),
            data.get("editar_telefono_proveedor").toString());

        return ResponseEntity.ok(Map.of("mensaje", "Proveedor editado correctamente"));
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearProveedor(@RequestBody Map<String, String> data) {
        try {
            proveedorService.crear(
                data.get("editar_nombre_proveedor"),
                data.get("editar_nit_proveedor"),
                data.get("editar_correo_proveedor"),
                data.get("editar_telefono_proveedor"));

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
    public ResponseEntity<Map<String, String>> asignarPrecio(@RequestBody Map<String, String> data) {
        try {
            int idProveedor = Integer.parseInt(data.get("id_proveedor_precio"));
            int idProducto = Integer.parseInt(data.get("id_producto_precio"));
            int precio = Integer.parseInt(data.get("precio_precio"));

            if (idProveedor == 0 || idProducto == 0) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Error: producto o proveedor no encontrado"));
            }

            boolean respuesta = proveedorProductoService.asignarPrecio(idProveedor, idProducto, precio);

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
