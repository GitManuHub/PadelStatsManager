package com.padelstats.stats_manager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Torneos {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "ubicacion")
    private String ubicacion;
    @Column(name = "url")
    private String url;
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
}
