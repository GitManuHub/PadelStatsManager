package com.padelstats.stats_manager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Partidos {
    @Id
    private String id;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ronda_id")
    private Rondas ronda;

    @ManyToOne
    @JoinColumn(name = "jugador1_id")
    private Jugadores jugador1;

    @ManyToOne
    @JoinColumn(name = "jugador2_id")
    private Jugadores jugador2;

    @ManyToOne
    @JoinColumn(name = "jugador3_id")
    private Jugadores jugador3;

    @ManyToOne
    @JoinColumn(name = "jugador4_id")
    private Jugadores jugador4;

    private int parejaGanadoraId;

    @OneToMany(mappedBy = "partido")
    private List<Sets> sets;
}
