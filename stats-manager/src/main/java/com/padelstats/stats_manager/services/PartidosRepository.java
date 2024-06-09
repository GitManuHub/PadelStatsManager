package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Partidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidosRepository extends JpaRepository<Partidos, String> {
    List<Partidos> findByJugador1IdOrJugador2IdOrJugador3IdOrJugador4Id(String jugadorId1, String jugadorId2, String jugadorId3, String jugadorId4);
}
