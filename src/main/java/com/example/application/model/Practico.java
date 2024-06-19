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
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor 
//@Builder
@Table

public class Practico implements Serializable {

    @PrimaryKey
    private Long id;

    @Column("tema")
    private String tema;

    @Column("descripcion")
    private String descripcion;

    @Column("desde")
    private LocalDateTime desde;

    @Column("hasta")
    private LocalDateTime hasta;

}