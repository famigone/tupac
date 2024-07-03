package com.example.application.model;


import java.io.Serializable;

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

public class Desafio implements Serializable {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long id;

    @PrimaryKeyColumn(name = "materiaid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long materiaid;

    @PrimaryKeyColumn(name = "practicoid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long practicoid;

    @Column("desafio")
    private String desafio;

    @Column("orden")
    private Integer orden;         

}