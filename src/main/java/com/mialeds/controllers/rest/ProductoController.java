package com.mialeds.controllers.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Producto;
import com.mialeds.services.KardexService;
import com.mialeds.services.ProductoService;
import com.mialeds.services.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public List<Producto> listarproductos() {
        return productoService.listar();
    }

    @GetMapping("/productos-escasos")
    public List<Producto> productosEscasos() {
        return productoService.productosEscasos();
    }
    // Buscar productos por nombre
    @GetMapping("/buscar")
    public List<Producto> buscarProducto(@RequestParam("nombre") String nombre) {
        return productoService.listarPorNombre(nombre);    
    }

    //metodo utilizado para listar cuando se busca un producto
    @GetMapping("/listar-buscador")
    public List<Producto> listarProductosBusqueda() {
        return productoService.listarIdNombrePresentacion();
    }

    @PostMapping("/nuevo")
    public Map<String, String> crearProducto(@RequestBody Map<String, String> data) {
        productoService.crear(
                data.get("nombre").toString(),
                data.get("presentacion").toString(),
                Integer.parseInt(data.get("precioCompra").toString()),
                Integer.parseInt(data.get("precioVenta").toString()),
                Integer.parseInt(data.get("cantidad").toString())
        );
        return Map.of("mensaje", "Producto creado correctamente");
    }
    
    @PutMapping("/editar/{id}")
    public Map<String, String> editarProducto(
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
        return Map.of("mensaje", "Producto actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarProducto(@PathVariable("id") int id) {
        productoService.eliminar(id);
        return Map.of("mensaje", "Producto eliminado correctamente");
    }

    // Registrar movimiento de entrada y salida (el comportamiento de ventas se maneja en otro metodo)
    @PostMapping("/movimiento-producto/{id}")
    public Map<String, String> movimientoProducto(
        @PathVariable("id") int id,
            @RequestBody Map<String, Object> data) {
        int cantidad = Integer.parseInt(data.get("cantidad").toString());
        String tipoMovimiento = data.get("movimiento").toString();
        LocalDate fecha = LocalDate.parse(data.get("fecha").toString());

        productoService.movimiento(id, cantidad, tipoMovimiento);
        int idUsuario = usuarioService.obtenerIdUsuarioSesion();
        kardexService.crear(id, idUsuario, tipoMovimiento, fecha, cantidad);

        return Map.of("mensaje", "Movimiento registrado correctamente");
    }
}
