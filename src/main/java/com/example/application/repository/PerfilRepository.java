package com.example.application.repository;

import com.example.application.model.Perfil;
import com.github.javaparser.printer.SourcePrinter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends CassandraRepository<Perfil, Long> {
    
    Perfil save(Perfil perfil);    
    Optional<Perfil> findById(String id);
}
