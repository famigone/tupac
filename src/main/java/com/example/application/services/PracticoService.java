package com.example.application.services;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.application.model.Practico;
import com.example.application.repository.PracticoRepository;
import com.example.application.services.PracticoService.PracticoRecord;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.BrowserCallable;
import jakarta.validation.constraints.NotNull;


import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class PracticoService {

    private final PracticoRepository repository;
 
    

    public record PracticoRecord(
            Long id,
            Long materia_id,
            @NotNull             
            String nombre,                        
            @NotNull             
            String descripcion,                        
            String tema,
            LocalDateTime desde, 
            LocalDateTime hasta 
            ) {
    }

    public PracticoService(PracticoRepository repository) {
        this.repository = repository;
    }

    private PracticoRecord toPracticoRecord(Practico laPractico) {
        return new PracticoRecord(
                laPractico.getId(),
                laPractico.getMateria_id(),
                laPractico.getNombre(),                                
                laPractico.getDescripcion(),
                laPractico.getTema(),
                laPractico.getDesde(),
                laPractico.getHasta()

                );
    }

    private static Long generateRandomLong() {
      /*   UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return Math.abs(mostSignificantBits ^ leastSignificantBits);
        */
        return ThreadLocalRandom.current().nextLong((long)666666);  
    }

    public PracticoRecord savePractico(PracticoRecord nuevaPractico) {
        // Crea un nuevo objeto Practico y asigna los valores del objeto recibido
        Practico dbPractico = new Practico();
        
        dbPractico.setId(generateRandomLong());      
        dbPractico.setTema(nuevaPractico.tema);        
        dbPractico.setNombre(nuevaPractico.nombre);        
        dbPractico.setMateria_id(nuevaPractico.materia_id);        
        dbPractico.setDescripcion(nuevaPractico.descripcion);                
        dbPractico.setDesde(nuevaPractico.desde);        
        dbPractico.setHasta(nuevaPractico.hasta);
        // Guarda el nuevo organismo en la base de datos        
        Practico savedPractico = repository.save(dbPractico);

        // Devuelve el organismo guardado después de convertirlo a PracticoRecord 
        return toPracticoRecord(savedPractico);
    }

    private PracticoRecord updatePractico(PracticoRecord laPractico) {        
        var dbPractico = repository.findById(laPractico.id).orElseThrow();        
        dbPractico.setNombre(laPractico.nombre);        
        dbPractico.setTema(laPractico.tema);        
        dbPractico.setMateria_id(laPractico.materia_id);
        dbPractico.setDesde(laPractico.desde);        
        dbPractico.setHasta(laPractico.hasta);        
        dbPractico.setDescripcion(laPractico.descripcion);                
        Practico savedPractico = repository.save(dbPractico);
        
        return toPracticoRecord(savedPractico);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<PracticoRecord> findAllPracticos() {
        Stream<Practico> listaResul = repository.findAll().stream();
        
        return listaResul.map(this::toPracticoRecord).toList();
    }

    public PracticoRecord save(PracticoRecord laPractico) {
       
        PracticoRecord rta;
        // var dbPractico = repository.findById(elPractico.id);
        if (!(laPractico.id > 0))   {         
            
            rta = this.savePractico(laPractico);
        }else{
            
            rta = this.updatePractico(laPractico);            
        }    
        return rta;
    }


}