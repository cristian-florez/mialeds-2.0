package com.mialeds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mialeds.models.Kardex;
import com.mialeds.services.KardexService;

@RestController
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    private KardexService kardexSerive;

    @GetMapping("/kardex-movimiento")
    public ResponseEntity<List<Kardex>> listarMovimientos(@RequestParam("movimiento") String movimiento) {
        List<Kardex> resultado = kardexSerive.listarPorMovimiento(movimiento);

        if (resultado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(resultado); // 204 sin contenido
        } else {
            return ResponseEntity.ok(resultado); // 200 OK con datos
        }
    } 
}
