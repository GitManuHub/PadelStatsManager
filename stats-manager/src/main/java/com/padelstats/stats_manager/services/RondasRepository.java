package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Partidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RondasRepository extends JpaRepository<Partidos, Integer> {
    @Query(value = "SELECT id FROM rondas WHERE torneo_id = :torneoId AND nombre = :nombre", nativeQuery = true)
    Integer findRondaIdByTorneoIdAndNombre(@Param("torneoId") String torneoId, @Param("nombre") String nombre);
}
