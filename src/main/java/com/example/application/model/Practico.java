package com.example.application.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor 
//@Builder
@Table

public class Practico implements Serializable {

    
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long id;

    @PrimaryKeyColumn(name = "materiaId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long materiaId;

    @Column("tema")
    private String tema;

    @Column("nombre")    
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("desde")
    private LocalDateTime desde;

    @Column("hasta")
    private LocalDateTime hasta;

}