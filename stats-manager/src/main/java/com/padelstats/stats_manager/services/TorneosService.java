package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.dto.TorneoDTO;
import com.padelstats.stats_manager.entities.Torneos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TorneosService {

    @Autowired
    private TorneosRepository torneosRepository;

    public List<Torneos> getAllTorneos() {
        return torneosRepository.findAll();
    }
    public List<String> getTorneosPasados() {
        return torneosRepository.torneosPasados();
    }

    public List<TorneoDTO> getTorneosPasadosConId() {
        List<Object[]> results = torneosRepository.torneosPasadosConId();
        List<TorneoDTO> torneoDTOs = new ArrayList<>();
        for (Object[] result : results) {
            String id = (String) result[0];
            String url = (String) result[1];
            torneoDTOs.add(new TorneoDTO(id, url));
        }
        return torneoDTOs;
    }


}
