package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.dto.TorneoDTO;
import com.padelstats.stats_manager.entities.Torneos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TorneosRepository extends JpaRepository<Torneos, String> {
    List<Torneos> findAll();

//    @Query("SELECT * FROM torneos " +
//            "WHERE fecha_fin <= CURRENT_DATE + 3 " +
//            "ORDER BY fecha_fin ASC")
    @Query(value = "SELECT url FROM torneos WHERE fecha_fin <= CURRENT_DATE + INTERVAL '3 days' ORDER BY fecha_fin ASC", nativeQuery = true)
    List<String> torneosPasados();

    @Query(value = "SELECT t.id, t.url FROM torneos t WHERE t.fecha_fin <= CURRENT_DATE - INTERVAL '3 days' ORDER BY t.fecha_fin ASC", nativeQuery = true)
    List<Object[]> torneosPasadosConId();
}

