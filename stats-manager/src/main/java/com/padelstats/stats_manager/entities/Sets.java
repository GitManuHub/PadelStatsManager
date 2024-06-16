package com.padelstats.stats_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int setNumero;
    private int puntosPareja1;
    private int puntosPareja2;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    @JsonIgnore
    private Partidos partido;

    public Sets(int setNumero, int puntosPareja1, int puntosPareja2, Partidos partido) {
        this.setNumero = setNumero;
        this.puntosPareja1 = puntosPareja1;
        this.puntosPareja2 = puntosPareja2;
        this.partido = partido;
    }

    public Sets(int setNumero, int puntosPareja1, int puntosPareja2) {
        this.setNumero = setNumero;
        this.puntosPareja1 = puntosPareja1;
        this.puntosPareja2 = puntosPareja2;
    }

    @Override
    public String toString() {
        String partido = getPartido().getId() != 0 ? getPartido().getId() + "": "";
        return "Sets{" +
                "set " + setNumero +
                " R: " + puntosPareja1 +
                " - " + puntosPareja2 +
                //", partido=" + partido +
                '}';
    }
}
