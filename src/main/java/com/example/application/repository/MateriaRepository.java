package com.example.application.repository;

import com.example.application.model.Materia;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends CassandraRepository<Materia, Long> {
}
