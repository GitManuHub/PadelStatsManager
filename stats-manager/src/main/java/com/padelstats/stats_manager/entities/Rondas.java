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
public class Rondas {
    @Id
    private String id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneos torneo;

}
