package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.entities.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetsService {

    @Autowired
    SetsRepository setsRepository;

    public Sets guardar(Sets set) {
        return setsRepository.save(set);
    }
}
