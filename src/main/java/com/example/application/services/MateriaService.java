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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AnonymousAllowed
@BrowserCallable
public class MateriaService {

    private final MateriaRepository repository;
 
    

    public record MateriaRecord(
            Long id,
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

    private static Long generateRandomLong() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return Math.abs(mostSignificantBits ^ leastSignificantBits);
    }

    public MateriaRecord saveMateria(MateriaRecord nuevaMateria) {
        // Crea un nuevo objeto Materia y asigna los valores del objeto recibido
        Materia dbMateria = new Materia();
        
        dbMateria.setId(generateRandomLong());      
        dbMateria.setNombre(nuevaMateria.nombre);        
        dbMateria.setDescripcion(nuevaMateria.descripcion);        
        //dbMateria.setDesde(nuevaMateria.desde);        
        //dbMateria.setHasta(nuevaMateria.hasta);
        // Guarda el nuevo organismo en la base de datos
        System.out.println("ID "+dbMateria.getId());
        Materia savedMateria = repository.save(dbMateria);

        // Devuelve el organismo guardado después de convertirlo a MateriaRecord 
        return toMateriaRecord(savedMateria);
    }

    private MateriaRecord updateMateria(MateriaRecord laMateria) {
        System.out.println("va a actualizar a "+laMateria.id);
        var dbMateria = repository.findById(laMateria.id).orElseThrow();
        System.out.println("encontró a "+dbMateria.getId());
        dbMateria.setNombre(laMateria.nombre);        
       // dbMateria.setDesde(laMateria.desde);        
       // dbMateria.setHasta(laMateria.hasta);        
        dbMateria.setDescripcion(laMateria.descripcion);                
        Materia savedMateria = repository.save(dbMateria);
        
        return toMateriaRecord(savedMateria);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<MateriaRecord> findAllMaterias() {
        Stream<Materia> listaResul = repository.findAll().stream();
        
        return listaResul.map(this::toMateriaRecord).toList();
    }

    public MateriaRecord save(MateriaRecord laMateria) {
       
        MateriaRecord rta;
        // var dbMateria = repository.findById(elMateria.id);
        if (laMateria.id() == 0)   {         
            System.out.println("oneeeeeeeeee");
            rta = this.saveMateria(laMateria);
        }else{
            System.out.println("twooooooooooo");
            rta = this.updateMateria(laMateria);            
        }    
        return rta;
    }


}