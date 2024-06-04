package com.example.application.model;


import lombok.Builder;
import lombok.Data;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Date;


@Data
@Builder

@Table("materia")

public class Materia implements Serializable {

    @PrimaryKey
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("desde")
    private Date desde;

    @Column("hasta")
    private Date hasta;
    

    

}