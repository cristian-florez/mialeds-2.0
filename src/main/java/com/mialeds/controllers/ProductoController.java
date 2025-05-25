package com.mialeds.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Producto;
import com.mialeds.models.dtos.ProductoController.ProductoDTO;
import com.mialeds.models.dtos.ProductoController.ProductoMovimientoDTO;
import com.mialeds.services.KardexService;
import com.mialeds.services.ProductoService;
import com.mialeds.services.UsuarioService;

import jakarta.validation.Valid;

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
    public ResponseEntity<Map<String, String>> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        productoService.crear(
            productoDTO.getNombre(),
            productoDTO.getPresentacion(),
            productoDTO.getPrecioCompra(),
            productoDTO.getPrecioVenta(),
            productoDTO.getCantidad()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Map.of("mensaje", "Producto creado correctamente"));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarProducto(
            @PathVariable("id") int id,@Valid
            @RequestBody ProductoDTO productoDTO) {
        productoService.actualizar(
            id,
            productoDTO.getNombre(),
            productoDTO.getPresentacion(),
            productoDTO.getPrecioCompra(),
            productoDTO.getPrecioVenta(),
            productoDTO.getCantidad()
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
        @PathVariable("id") int id,@Valid
        @RequestBody ProductoMovimientoDTO productoMovimientoDTO) {
        int cantidad = productoMovimientoDTO.getCantidad();
        String tipoMovimiento = productoMovimientoDTO.getMovimiento();
        LocalDate fecha = productoMovimientoDTO.getFecha();

        productoService.movimiento(id, cantidad, tipoMovimiento);
        int idUsuario = usuarioService.obtenerIdUsuarioSesion();
        kardexService.crear(id, idUsuario, tipoMovimiento, fecha, cantidad);

        return ResponseEntity.ok(Map.of("mensaje", "Movimiento registrado correctamente"));
    }
}
