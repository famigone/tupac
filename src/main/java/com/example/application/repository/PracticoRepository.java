package com.example.application.repository;

import com.example.application.model.Practico;
import com.github.javaparser.printer.SourcePrinter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PracticoRepository extends CassandraRepository<Practico, Long> {
    
    Practico save(Practico practico);    
    Optional<Practico> findById(String id);
    
    @Query("SELECT * FROM practico WHERE materiaid = ?0 ALLOW FILTERING")
    List<Practico> findByMateriaid(Long materiaid);
}
