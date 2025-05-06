package com.mialeds.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Producto;
import com.mialeds.services.KardexService;
import com.mialeds.services.ProductoService;
import com.mialeds.services.UsuarioService;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private KardexService kardexService;

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarproductos() {
        List<Producto> productos = productoService.listar();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos-escasos")
    public ResponseEntity<List<Producto>> productosEscasos() {
        List<Producto> productos = productoService.productosEscasos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProducto(@RequestParam("nombre") String nombre) {
        List<Producto> productos = productoService.listarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/listar-buscador")
    public ResponseEntity<List<Producto>> listarProductosBusqueda() {
        List<Producto> productos = productoService.listarIdNombrePresentacion();
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearProducto(@RequestBody Map<String, String> data) {
        productoService.crear(
            data.get("nombre"),
            data.get("presentacion"),
            Integer.parseInt(data.get("precioCompra")),
            Integer.parseInt(data.get("precioVenta")),
            Integer.parseInt(data.get("cantidad"))
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Map.of("mensaje", "Producto creado correctamente"));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarProducto(
            @PathVariable("id") int id,
            @RequestBody Map<String, Object> data) {
        productoService.actualizar(
            id,
            data.get("nombre").toString(),
            data.get("presentacion").toString(),
            Integer.parseInt(data.get("precioCompra").toString()),
            Integer.parseInt(data.get("precioVenta").toString()),
            Integer.parseInt(data.get("cantidad").toString())
        );
        return ResponseEntity.ok(Map.of("mensaje", "Producto actualizado correctamente"));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable("id") int id) {
        productoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente"));
    }

    @PostMapping("/movimiento-producto/{id}")
    public ResponseEntity<Map<String, String>> movimientoProducto(
        @PathVariable("id") int id,
        @RequestBody Map<String, Object> data) {
        int cantidad = Integer.parseInt(data.get("cantidad").toString());
        String tipoMovimiento = data.get("movimiento").toString();
        LocalDate fecha = LocalDate.parse(data.get("fecha").toString());

        productoService.movimiento(id, cantidad, tipoMovimiento);
        int idUsuario = usuarioService.obtenerIdUsuarioSesion();
        kardexService.crear(id, idUsuario, tipoMovimiento, fecha, cantidad);

        return ResponseEntity.ok(Map.of("mensaje", "Movimiento registrado correctamente"));
    }
}
