package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.entities.Sets;
import com.padelstats.stats_manager.services.SetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetsController {

    @Autowired
    SetsService setsService;

    public ResponseEntity<Sets> guardar(Sets sets) {
        Sets set = setsService.guardar(sets);
        return new ResponseEntity<>(set, HttpStatus.CREATED);
    }
}
