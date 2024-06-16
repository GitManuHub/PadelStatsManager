package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.dto.TorneoDTO;
import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.entities.Torneos;
import com.padelstats.stats_manager.services.TorneosRepository;
import com.padelstats.stats_manager.services.TorneosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/torneos")
public class TorneosController {

    @Autowired
    private TorneosService torneosService;

    @GetMapping("")
    public List<Torneos> showAll() {
        return torneosService.getAllTorneos();
    }

    @GetMapping("/pasados")
    public List<String> torneosPasados() {
        return torneosService.getTorneosPasados();
    }

    @GetMapping("/pasados-con-id")
    public List<TorneoDTO> torneosPasadosConId() {
        return torneosService.getTorneosPasadosConId();
    }

}
