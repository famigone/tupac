package com.example.application.repository;

import com.example.application.model.Desafio;
import com.github.javaparser.printer.SourcePrinter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesafioRepository extends CassandraRepository<Desafio, Long> {
    
    Desafio save(Desafio desafio);    
    Optional<Desafio> findById(String id);
}
