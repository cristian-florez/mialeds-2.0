package com.mialeds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Proveedor;
import com.mialeds.models.ProveedorProducto;
import com.mialeds.services.ProveedorProductoService;
import com.mialeds.services.ProveedorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController{

    // Inyección de dependencias
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorProductoService proveedorProductoService;


    @GetMapping("/listar")
    public List<Proveedor> listarproveedores() {
        return proveedorService.listar();
    }

    //agrupamos los precios de los productos por proveedores para mostrar en la tabla
    @GetMapping("agrupar-productos")
    public Map<String, List<ProveedorProducto>> listarproductos() {
        return proveedorProductoService.obtenerProductosAgrupados();
    }

    //metodo que me mostrara todos los precios que me ofrecen los proveedores por el producto seleccionado
    @GetMapping("/buscar")
    public Map<String, List<ProveedorProducto>> buscarProducto(@RequestParam("producto") String nombre) {
        return proveedorProductoService.listarPornombre(nombre);
    }

    // Método para editar un producto
    @PutMapping("/editar/{id}")
    public Map<String, String> editarProveedor(
            @PathVariable("id") int id,
            @RequestBody Map<String, Object> data) {

        proveedorService.actualizar
        (id,
        data.get("editar_nombre_proveedor").toString(),
        data.get("editar_nit_proveedor").toString(),
        data.get("editar_correo_proveedor").toString(),
        data.get("editar_telefono_proveedor").toString());

        return Map.of("mensaje", "Proveedor editado correctamente");

    }

    // Método para crear un nuevo proveedor
    @PostMapping("/nuevo")
    public Map<String, String> crearProveedor(@RequestBody Map<String, String> data) {
        proveedorService.crear(
            data.get("editar_nombre_proveedor").toString(),
            data.get("editar_nit_proveedor").toString(),
            data.get("editar_correo_proveedor").toString(),
            data.get("editar_telefono_proveedor").toString());

            return Map.of("mensaje", "Error al crear el proveedor ");

        }


    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarProveedor(@PathVariable("id") int id) {
        proveedorService.eliminar(id);
        return Map.of("mensaje", "Error al eliminar el proveedor");
    }

    // Método para asignar precio de venta de proveedores a cierto producto
    @PutMapping("/asignarPrecio")
    public Map<String, String> asignarPrecio(@RequestBody Map<String, String> data){
            //validamos que el id del proveedor y del producto si existan
            if(Integer.parseInt(data.get("id_proveedor_precio").toString()) == 0 || Integer.parseInt(data.get("id_producto_precio").toString()) == 0){
                return Map.of("mensaje", "Error: producto o proveedor no encontrado");
            }
            //buscamos el producto por id y el proveedor por id y asignamos el precio
            boolean respuesta = proveedorProductoService.asignarPrecio(
                Integer.parseInt(data.get("id_proveedor_precio").toString()),
                Integer.parseInt(data.get("id_producto_precio").toString()),
                Integer.parseInt(data.get("precio_precio").toString()));
            if (respuesta) {
                return Map.of("mensaje", "Precio asignado correctamente");
            } else {
                return Map.of("mensaje","Error al asignar el precio: producto no encontrado");
            }
    }
}
