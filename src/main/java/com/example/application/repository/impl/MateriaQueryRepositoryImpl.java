package com.example.application.repository.impl;



import com.example.application.model.Materia;
import com.example.application.repository.MateriaQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MateriaQueryRepositoryImpl implements MateriaQueryRepository {
             
    @Autowired
    private CassandraOperations cassandraTemplate;

    @Override
    public List<Materia> save() {
        List<Materia> Materias = cassandraTemplate.select(Query.empty(), Materia.class);
        
        return cassandraTemplate.select(Query.empty(), Materia.class);
    }

    @Override
    public List<Materia> getAll() {
        return cassandraTemplate.select(Query.empty(), Materia.class);
    }

    @Override
    public List<Materia> getMateriaByNombre(String nombre) {
        return cassandraTemplate.select(Query.query(Criteria.where("nombre").is(nombre)).withAllowFiltering(), Materia.class);
    }

    @Override
    public Materia getOneMateriaByNombre(String nombre) {
        return cassandraTemplate.selectOne(Query.query(Criteria.where("nombre").is(nombre)).withAllowFiltering(), Materia.class);
    }

    @Override
    public List<Materia> getMateriaByNombreLike(String nombre) {
        return cassandraTemplate.select(Query.query(Criteria.where("nombre").like(nombre)).withAllowFiltering(), Materia.class);
    }

    @Override
    public Materia getSingleMateriaByNombre(String nombre) {
        return cassandraTemplate.selectOne(Query.query(Criteria.where("super_nombre").is(nombre)).withAllowFiltering(), Materia.class);
    }

    

    
}
