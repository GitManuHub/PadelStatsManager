package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.entities.Partidos;
import com.padelstats.stats_manager.services.PartidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class PartidosController {

    @Autowired
    private PartidosService partidosService;

    @GetMapping("/partidos/enfrentamientos")
    public List<Partidos> obtenerPartidosEntreJugadores(@RequestParam String jugadorId1, @RequestParam String jugadorId2) {
        return partidosService.encontrarPartidosEntreJugadores(jugadorId1, jugadorId2);
    }
}
