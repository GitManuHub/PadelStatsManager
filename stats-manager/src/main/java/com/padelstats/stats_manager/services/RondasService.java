package com.padelstats.stats_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RondasService {
    @Autowired
    private RondasRepository rondasRepository;

    public Integer getRondaId(String torneoId, String nombre) {
        return rondasRepository.findRondaIdByTorneoIdAndNombre(torneoId, nombre);
    }
}
