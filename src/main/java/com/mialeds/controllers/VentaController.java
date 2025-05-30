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
import com.mialeds.models.dtos.VentaController.VentaDTO;
import com.mialeds.models.dtos.VentaController.VentaEditarDTO;
import com.mialeds.services.VentaService;

import jakarta.validation.Valid;

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
    public ResponseEntity<Map<String, String>> crearVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        ventaService.guardar(
            ventaDTO.getIdProducto(),
            ventaDTO.getCantidad(),
            ventaDTO.getFecha()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensaje", "Venta realizada con éxito."));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Map<String, String>> editarVenta(@PathVariable int id,@Valid @RequestBody VentaEditarDTO ventaEditarDTO) {
        ventaService.actualizar(
            id,
            ventaEditarDTO.getIdProducto(),
            ventaEditarDTO.getCantidad(),
            ventaEditarDTO.getTotal(),
            ventaEditarDTO.getFecha()
        );
        return ResponseEntity.ok(Map.of("mensaje", "Venta editada con éxito."));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarVenta(@PathVariable("id_eliminar_venta") int id) {
        ventaService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Venta eliminada con éxito."));
    }

    @GetMapping("/Productos-mas-vendidos")
    public ResponseEntity<List<Venta>> top5ProductosMasVendidos() {
        return ResponseEntity.ok(ventaService.obtenerTop5ProductosMasVendidos());
    }

    @GetMapping("/Productos.menos-vendidos")
    public ResponseEntity<List<Venta>> top5ProductosMenosVendidos() {
        return ResponseEntity.ok(ventaService.obtenerTop5ProductosMenosVendidos());
    }

    @GetMapping("/Ventas-ultimos-30-dias")
    public ResponseEntity<List<Venta>> ventasPorDiaUltimos30Dias() {
        return ResponseEntity.ok(ventaService.obtenerVentasUltimoMes());
    }

    @GetMapping("/Ganancias-ultimos-30-dias")
    public ResponseEntity<List<Venta>> gananciasPorDiaUltimos30Dias() {
        return ResponseEntity.ok(ventaService.obtenerGananciasUltimoMes());
    }
}
