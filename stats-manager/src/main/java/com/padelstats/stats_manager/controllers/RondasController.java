package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.services.RondasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RondasController {

    @Autowired
    private RondasService rondaService;

    @GetMapping("/rondaId")
    public Integer getRondaId(@RequestParam String torneoId, @RequestParam String nombre) {
        return rondaService.getRondaId(torneoId, nombre);
    }
}
