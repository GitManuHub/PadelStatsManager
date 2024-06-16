package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "jugadores",collectionResourceRel = "jugadores")
@Service
public interface IJugadorService extends JpaRepository<Jugadores, String> {
    @Query(value = "SELECT id FROM jugadores WHERE nombre ILIKE '%' || :letra || '%' AND nombre ILIKE '%' || :apellido || '%' ORDER BY LENGTH(nombre) LIMIT 1", nativeQuery = true)
    String findJugadorIdByNombreSimilar(@Param("letra") String letra, @Param("apellido") String apellido);
}
