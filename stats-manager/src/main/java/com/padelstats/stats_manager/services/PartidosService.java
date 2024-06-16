package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Partidos;
import com.padelstats.stats_manager.entities.Torneos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidosService {

    @Autowired
    private PartidosRepository partidosRepository;

//    public List<Partidos> encontrarPartidosEntreJugadores(String jugadorId1, String jugadorId2) {
//        return partidosRepository.findByJugador1IdOrJugador2IdOrJugador3IdOrJugador4Id(jugadorId1, jugadorId2, jugadorId1, jugadorId2);
//    }
    public List<Partidos> encontrarPartidosEntreJugadores(String jugadorId1, String jugadorId2) {
        return partidosRepository.findPartidosEntreJugadores(jugadorId1, jugadorId2);
    }

    public Partidos guardar(Partidos partido) {
        return partidosRepository.save(partido);
    }
}
