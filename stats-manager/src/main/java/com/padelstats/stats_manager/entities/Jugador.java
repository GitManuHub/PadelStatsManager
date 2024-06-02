package com.padelstats.stats_manager.entities;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int posicionRanking;
    private int puntos;
    private String nombre;
    private String nacionalidad;
    private String posicionPista;
    @OneToOne
    @JoinColumn(name = "pareja_id")
    private Jugador pareja;
    private String fechaNacimiento;
    private double altura;
    private String lugarNac;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosPerdidos;
    private int victoriasConsecutivas;
    private double efectividad;
    @OneToMany
    private List<RondaPuntuable> desglosePuntos;


    public Jugador(int posicionRanking, int puntos, String nombre, String nacionalidad) {
        this.posicionRanking = posicionRanking;
        this.puntos = puntos;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }
}
