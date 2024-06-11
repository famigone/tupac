package com.example.application.repository;

import com.example.application.model.Materia;
import com.example.application.model.User;
import com.github.javaparser.printer.SourcePrinter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Long> {
    
    User save(User usuario);    
    Optional<User> findById(Long id);
    User findByUsername(String subject);
}
