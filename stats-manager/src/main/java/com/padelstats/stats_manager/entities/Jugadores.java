package com.padelstats.stats_manager.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int posicionRanking;
    private int puntos;
    private String nombre;
    private String nacionalidad;
    private String rutaBandera;
    private String posicionPista;
    private int pareja;
    private String fechaNac;
    private double altura;
    private String lugarNac;
    private int partidosJugados;
    private int partidosGanados;
    private int victoriasConsecutivas;
    private String rutaFoto;

    public Jugadores(int posicionRanking, int puntos, String nombre, String nacionalidad, String rutaBandera,
                     String posicionPista, int pareja, String fechaNac, double altura, String lugarNac,
                     int partidosJugados, int partidosGanados, int victoriasConsecutivas, String rutaFoto) {
        this.posicionRanking = posicionRanking;
        this.puntos = puntos;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.rutaBandera = rutaBandera;
        this.posicionPista = posicionPista;
        this.pareja = pareja;
        this.fechaNac = fechaNac;
        this.altura = altura;
        this.lugarNac = lugarNac;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.victoriasConsecutivas = victoriasConsecutivas;
        this.rutaFoto = rutaFoto;
    }
}
