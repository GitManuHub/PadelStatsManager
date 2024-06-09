package com.padelstats.stats_manager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sets {
    @Id
    private String id;
    private int setNumero;
    private int puntosPareja1;
    private int puntosPareja2;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partidos partido;
}
