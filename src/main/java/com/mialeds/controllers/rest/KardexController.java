package com.mialeds.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mialeds.models.Kardex;
import com.mialeds.services.KardexService;

@RestController
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    private KardexService kardexSerive;

    @GetMapping("/kardex-movimiento")
    public List<Kardex> listarMovimientos(@RequestParam("movimiento") String movimiento) {
        return kardexSerive.listarPorMovimiento(movimiento);
    } 
}
