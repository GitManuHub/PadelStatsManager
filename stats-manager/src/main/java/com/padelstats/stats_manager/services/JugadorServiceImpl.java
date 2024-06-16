package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Jugadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorServiceImpl {

    @Autowired
    private IJugadorService iJugadorService;

    public List<Jugadores> findAll() {
        return iJugadorService.findAll();
    }

    public Optional<Jugadores> findById(String id) {
        return iJugadorService.findById(id);
    }

    public Jugadores save(Jugadores jugador) {
        return iJugadorService.save(jugador);
    }

    public List<Jugadores> saveAll(List<Jugadores> jugadores) {
        return iJugadorService.saveAll(jugadores);
    }

    public void deleteById(String id) {
        iJugadorService.deleteById(id);
    }

    public String findJugadorIdByNombreSimilar(String letra, String apellido) {
        return iJugadorService.findJugadorIdByNombreSimilar(letra, apellido);
    }

    public Jugadores saveAndFlush(Jugadores jugador) {
        return iJugadorService.saveAndFlush(jugador);
    }
}
