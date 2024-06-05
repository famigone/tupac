package com.example.application.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Column;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor 
//@Builder
@Table("materia")

public class Materia {

    @PrimaryKey
    @Column("id")
    private UUID id;


    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;


    @Transient
    public void generateId() {
        this.id = UUID.randomUUID();
    }

}