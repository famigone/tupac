package com.example.application.services;


import com.example.application.model.Materia;
import com.example.application.repository.MateriaRepository;

import com.example.application.services.MateriaService.MateriaRecord;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.BrowserCallable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AnonymousAllowed
@BrowserCallable
public class MateriaService {

    private final MateriaRepository repository;

    public record MateriaRecord(
            UUID id,
            // nombre de la aplicación
            @NotNull             
            String nombre,            
            
            @NotNull             
            String descripcion

            ) {
    }

    public MateriaService(MateriaRepository repository) {
        this.repository = repository;
    }

    private MateriaRecord toMateriaRecord(Materia laMateria) {
        return new MateriaRecord(
                laMateria.getId(),
                laMateria.getNombre(), 
                laMateria.getDescripcion() 

                );
    }

    private MateriaRecord saveMateria(MateriaRecord nuevaMateria) {
        // Crea un nuevo objeto Materia y asigna los valores del objeto recibido
        Materia dbMateria = new Materia();
        dbMateria.setNombre(nuevaMateria.nombre);        
        dbMateria.setDescripcion(nuevaMateria.descripcion);        
        //dbMateria.setDesde(nuevaMateria.desde);        
        //dbMateria.setHasta(nuevaMateria.hasta);
        // Guarda el nuevo organismo en la base de datos
        Materia savedMateria = repository.save(dbMateria);

        // Devuelve el organismo guardado después de convertirlo a MateriaRecord 
        return toMateriaRecord(savedMateria);
    }

    private MateriaRecord updateMateria(MateriaRecord laMateria) {
        var dbMateria = repository.findById(laMateria.id).orElseThrow();

        dbMateria.setNombre(laMateria.nombre);        
       // dbMateria.setDesde(laMateria.desde);        
       // dbMateria.setHasta(laMateria.hasta);        
        dbMateria.setNombre(laMateria.descripcion);        

        Materia savedMateria = repository.save(dbMateria);
        
        return toMateriaRecord(savedMateria);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
    
    public List<MateriaRecord> findAllMaterias() {
        Stream<Materia> listaResul = repository.findAll().stream();
        
        return listaResul.map(this::toMateriaRecord).toList();
    }

    public MateriaRecord save(MateriaRecord laMateria) {
        MateriaRecord rta;
        // var dbMateria = repository.findById(elMateria.id);
        if (laMateria.id() == null)
            rta = this.saveMateria(laMateria);
        else
            rta = this.updateMateria(laMateria);
        return rta;
    }


}