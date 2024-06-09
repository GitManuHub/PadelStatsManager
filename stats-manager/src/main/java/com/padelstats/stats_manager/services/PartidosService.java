package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Partidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidosService {

    @Autowired
    private PartidosRepository partidosRepository;

    public List<Partidos> encontrarPartidosEntreJugadores(String jugadorId1, String jugadorId2) {
        return partidosRepository.findByJugador1IdOrJugador2IdOrJugador3IdOrJugador4Id(jugadorId1, jugadorId2, jugadorId1, jugadorId2);
    }
}
