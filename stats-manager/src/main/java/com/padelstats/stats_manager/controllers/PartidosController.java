package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.entities.Partidos;
import com.padelstats.stats_manager.services.PartidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class PartidosController {

    @Autowired
    private PartidosService partidosService;

    @PostMapping("insertar")
    public ResponseEntity<Partidos> insertarPartido(@RequestBody Partidos partidos) {
        Partidos nuevoPartido = partidosService.guardar(partidos);
        return new ResponseEntity<>(nuevoPartido, HttpStatus.CREATED);
    }

    @GetMapping("/partidos/enfrentamientos")
    public List<Partidos> obtenerPartidosEntreJugadores(@RequestParam String jugadorId1, @RequestParam String jugadorId2) {
        return partidosService.encontrarPartidosEntreJugadores(jugadorId1, jugadorId2);
    }
}
