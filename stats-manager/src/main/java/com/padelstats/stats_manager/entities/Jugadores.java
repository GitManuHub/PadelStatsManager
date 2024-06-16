package com.padelstats.stats_manager.entities;


import jakarta.persistence.Column;
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

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugadores {

    @Id
    private String id;
    /*@GeneratedValue(strategy = GenerationType.TABLE)
    private int id;*/

    @Column(name = "posicion_ranking")
    private int posicionRanking;

    @Column(name = "variacion_puesto")
    private String variacionPuesto;

    @Column(name = "puntos")
    private int puntos;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "ruta_bandera")
    private String rutaBandera;

    @Column(name = "posicion_pista")
    private String posicionPista;

    @OneToOne
    @JoinColumn(name = "pareja_id")
    private Jugadores pareja;

    @Column(name = "fecha_nac")
    private String fechaNac;

    @Column(name = "altura")
    private double altura;

    @Column(name = "lugar_nac")
    private String lugarNac;

    @Column(name = "partidos_jugados")
    private int partidosJugados;

    @Column(name = "partidos_ganados")
    private int partidosGanados;

    @Column(name = "victorias_consecutivas")
    private int victoriasConsecutivas;

    @Column(name = "ruta_foto")
    private String rutaFoto;

    public Jugadores(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Jugadores(int posicionRanking, String variacionPuesto, int puntos, String nombre, String nacionalidad, String rutaBandera,
                     String posicionPista, Jugadores pareja, String fechaNac, double altura, String lugarNac,
                     int partidosJugados, int partidosGanados, int victoriasConsecutivas, String rutaFoto) {
        this.posicionRanking = posicionRanking;
        this.variacionPuesto = variacionPuesto;
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

    public Jugadores(String id) {
        this.id = id;
    }
}
