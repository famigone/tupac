package com.example.application.repository;

import com.example.application.model.Desafio;
import com.example.application.model.Practico;
import com.github.javaparser.printer.SourcePrinter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DesafioRepository extends CassandraRepository<Desafio, Long> {
    
    Desafio save(Desafio desafio);    
    Optional<Desafio> findById(String id);

       @Query("SELECT * FROM desafio WHERE practicoid = ?0 ALLOW FILTERING")
    List<Desafio> findByPracticoid(Long practicoid);
}
