package com.mialeds.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Venta;
import com.mialeds.services.VentaService;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarPorFecha(LocalDate.now());
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/total-ventas")
    public ResponseEntity<String> totalVentas(@RequestBody List<Venta> ventas) {
        String total = ventaService.formatearTotalVentas(ventas);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Venta>> buscarVenta(
        @RequestParam(value = "producto_nombre", required = false) String nombre,
        @RequestParam(value = "fecha_busqueda", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<Venta> resultados = ventaService.obtenerVentas(nombre, fecha);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Map<String, String>> crearVenta(@RequestBody Map<String, String> data) {
        ventaService.guardar(
            Integer.parseInt(data.get("id_producto_venta")),
            Integer.parseInt(data.get("cantidad_entrada_venta")),
            LocalDate.parse(data.get("fecha_venta"))
        );
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensaje", "Venta realizada con éxito."));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarVenta(@PathVariable int id, @RequestBody Map<String, Object> data) {
        ventaService.actualizar(
            id,
            Integer.parseInt(data.get("id_producto_venta").toString()),
            Integer.parseInt(data.get("cantidad_editar_venta").toString()),
            Integer.parseInt(data.get("cantidad_editar_total_venta").toString()),
            LocalDate.parse(data.get("fecha_editar_venta").toString())
        );
        return ResponseEntity.ok(Map.of("mensaje", "Venta editada con éxito."));
    }

    @DeleteMapping("/eliminar/{id_eliminar_venta}")
    public ResponseEntity<Map<String, String>> eliminarVenta(@PathVariable("id_eliminar_venta") int id) {
        ventaService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Venta eliminada con éxito."));
    }

    @GetMapping("/ProductosMasVendidos")
    public ResponseEntity<List<Venta>> top5ProductosMasVendidos() {
        return ResponseEntity.ok(ventaService.obtenerTop5ProductosMasVendidos());
    }

    @GetMapping("/ProductosMenosVendidos")
    public ResponseEntity<List<Venta>> top5ProductosMenosVendidos() {
        return ResponseEntity.ok(ventaService.obtenerTop5ProductosMenosVendidos());
    }

    @GetMapping("/VentasUltimos30Dias")
    public ResponseEntity<List<Venta>> ventasPorDiaUltimos30Dias() {
        return ResponseEntity.ok(ventaService.obtenerVentasUltimoMes());
    }

    @GetMapping("/GananciasUltimos30Dias")
    public ResponseEntity<List<Venta>> gananciasPorDiaUltimos30Dias() {
        return ResponseEntity.ok(ventaService.obtenerGananciasUltimoMes());
    }
}
