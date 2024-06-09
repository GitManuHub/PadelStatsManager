package com.padelstats.stats_manager.entities;

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
    private String id;
    private String nombre;
    private String ubicacion;
    private Date fechaInicio;
    private Date fechaFin;
}
