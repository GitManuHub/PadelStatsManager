package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

@RepositoryRestResource(path = "jugadores",collectionResourceRel = "jugadores")
@Service
public interface IJugadorService extends JpaRepository<Jugadores, Long> {

}
