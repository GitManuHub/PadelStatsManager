package com.padelstats.stats_manager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    public Partidos(Date fecha, Rondas ronda, Jugadores jugador1, Jugadores jugador2, Jugadores jugador3, Jugadores jugador4, int parejaGanadoraId, List<Sets> sets) {
        this.fecha = fecha;
        this.ronda = ronda;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
        this.parejaGanadoraId = parejaGanadoraId;
        this.sets = sets;
    }

    public Partidos(Date fecha, Rondas ronda, Jugadores jugador1, Jugadores jugador2, Jugadores jugador3, Jugadores jugador4, int parejaGanadoraId) {
        this.fecha = fecha;
        this.ronda = ronda;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
        this.parejaGanadoraId = parejaGanadoraId;
    }

    public Partidos(Date fecha, Jugadores jugador1, Jugadores jugador2, Jugadores jugador3, Jugadores jugador4, int parejaGanadoraId) {
        this.fecha = fecha;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
        this.parejaGanadoraId = parejaGanadoraId;
    }

    @Override
    public String toString() {
        String nombreRonda = ronda != null ? ronda.getNombre() : "no viene";
        String setsString = "";
        if (!sets.isEmpty()) {
            for (int i = 0; i < sets.size(); i++) {
                setsString += sets.get(i).toString();
            }
        }
        return "Partidos{" +
                "id='" + id + '\'' +
                ", fecha=" + fecha +
                ", ronda=" + nombreRonda +
                ", jugador1=" + jugador1.getNombre() +
                ", jugador2=" + jugador2.getNombre() +
                ", jugador3=" + jugador3.getNombre() +
                ", jugador4=" + jugador4.getNombre() +
                ", parejaGanadoraId=" + parejaGanadoraId +
                ", sets=" + setsString +
                '}';
    }
}
