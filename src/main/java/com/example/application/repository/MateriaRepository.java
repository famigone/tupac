package com.example.application.repository;

import com.example.application.model.Materia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends CassandraRepository<Materia, UUID> {
    Materia save(Materia materia);

    Optional<Materia> findById(UUID id);
}
