package com.mialeds.controllers.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Venta;
import com.mialeds.services.VentaService;




@RestController
@RequestMapping("/venta")
public class VentaController{

    @Autowired
    private VentaService ventaService;

    //metodo para listar teniendo en cuenta la fecha actual
    @GetMapping("/listar")
    public List<Venta> listarVentas() {
        return ventaService.listarPorFecha(LocalDate.now());
    }
    
    //metodo para traer el total de una lista de ventas
    @GetMapping("/total-ventas")
    public String totalVentas(List<Venta> ventas){
        return ventaService.formatearTotalVentas(ventas);
    }
    
    // Endpoint para buscar ventas por nombre de producto y/o fecha
    @GetMapping("/buscar")
    public List<Venta> buscarVenta(
            @RequestParam(value = "producto_nombre", required = false) String nombre,
            @RequestParam(value = "fecha_busqueda", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

            return ventaService.obtenerVentas(nombre, fecha);
    }
    

    //los parametros que no se pasan como id del usuario se obtiene mediante otra manera mediante el metodo de ventaService.guardar
    @PostMapping("/nuevo")
    public Map<String, String> crearVenta(@RequestBody Map<String, String> data) {
        ventaService.guardar(
            Integer.parseInt(data.get("id_producto_venta").toString()),
            Integer.parseInt(data.get("cantidad_entrada_venta").toString()),
            LocalDate.parse(data.get("fecha_venta").toString())
            );
        return Map.of("mensaje", "Venta realizada con éxito.");
    }
    
   //metodo para editar la venta
   @PutMapping("/editar/{id}")
   public Map<String, String> editarVenta(@PathVariable int id, @RequestBody Map<String, Object> data) {
        ventaService.actualizar
        (id,
        Integer.parseInt(data.get("id_producto_venta").toString()),
        Integer.parseInt(data.get("cantidad_editar_venta").toString()),
        Integer.parseInt(data.get("cantidad_editar_total_venta").toString()),
        LocalDate.parse(data.get("fecha_editar_venta").toString())
        );
    return Map.of("mensaje", "Venta editada con éxito.");
    }

    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarVenta(@PathVariable("id_eliminar_venta") int id, @RequestBody Map<String, Object> data) {
        ventaService.eliminar(id);
        return Map.of("mensaje", "Venta eliminada con éxito.");
    }
    
    //metodo para obtener los 5 productos mas vendidos
    @GetMapping("/ProductosMasVendidos")
    public List<Venta> top5ProductosMasVendidos() {
        return ventaService.obtenerTop5ProductosMasVendidos();
    }

    //metodo para obtener los 5 productos menos vendidos
    @GetMapping("/ProductosMenosVendidos")
    public List<Venta> top5ProductosMenosVendidos() {
        return ventaService.obtenerTop5ProductosMenosVendidos();
    }

    //metodo para obtener las ventas por dia de los ultimos 30 dias
    @GetMapping("/VentasUltimos30Dias")
    public List<Venta> ventasPorDiaUltimos30Dias() {
        return ventaService.obtenerVentasUltimoMes();
    }

    //metodo para obtener las ganancias por dia de los ultimos 30 dias
    @GetMapping("/GananciasUltimos30Dias")
    public List<Venta> gananciasPorDiaUltimos30Dias() {
        return ventaService.obtenerGananciasUltimoMes();
    }
    
 }
    


