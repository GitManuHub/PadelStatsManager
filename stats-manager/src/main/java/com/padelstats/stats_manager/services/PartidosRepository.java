package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Partidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartidosRepository extends JpaRepository<Partidos, Integer> {
    List<Partidos> findByJugador1IdOrJugador2IdOrJugador3IdOrJugador4Id(String jugadorId1, String jugadorId2, String jugadorId3, String jugadorId4);
    @Query("SELECT p FROM Partidos p WHERE " +
            "(:jugadorId1 IN (p.jugador1.id, p.jugador2.id) AND :jugadorId2 IN (p.jugador3.id, p.jugador4.id)) OR " +
            "(:jugadorId1 IN (p.jugador3.id, p.jugador4.id) AND :jugadorId2 IN (p.jugador1.id, p.jugador2.id))")
    List<Partidos> findPartidosEntreJugadores(@Param("jugadorId1") String jugadorId1, @Param("jugadorId2") String jugadorId2);


}
