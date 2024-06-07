package com.example.application.repository;

import com.example.application.model.Materia;
import com.github.javaparser.printer.SourcePrinter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends CassandraRepository<Materia, Long> {
    
    Materia save(Materia materia);    
    Optional<Materia> findById(String id);
}
