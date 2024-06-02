package com.padelstats.stats_manager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class RondaPuntuable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombreTorneo;
    private String categoria;
    private Date fecha;
    private String ronda;
    private int puntos;
}
