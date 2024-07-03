package com.example.application.services;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.application.model.Desafio;
import com.example.application.model.Desafio;
import com.example.application.repository.DesafioRepository;
import com.example.application.repository.DesafioRepository;

import com.example.application.services.DesafioService.DesafioRecord;
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
public class DesafioService {

    private final DesafioRepository repository;
 
    

    public record DesafioRecord(
            Long id,
            Long materiaid,
            Long practicoid,
            @NotNull             
            String desafio,                        
            int orden            
            ) {
    }

    public DesafioService (DesafioRepository repository) {
        this.repository = repository;
    }

    private DesafioRecord toDesafioRecord(Desafio laDesafio) {
        return new DesafioRecord(
                laDesafio.getId(),
                laDesafio.getMateriaid(),
                laDesafio.getPracticoid(),
                laDesafio.getDesafio(),                                
                laDesafio.getOrden()                

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

    public DesafioRecord saveDesafio(DesafioRecord nuevaDesafio) {
        // Crea un nuevo objeto Desafio y asigna los valores del objeto recibido
        Desafio dbDesafio = new Desafio();
        
        dbDesafio.setId(generateRandomLong());      
        dbDesafio.setMateriaid(nuevaDesafio.materiaid);        
        dbDesafio.setPracticoid(nuevaDesafio.practicoid);        
        dbDesafio.setOrden(nuevaDesafio.orden);        
        dbDesafio.setDesafio(nuevaDesafio.desafio);                
        
        // Guarda el nuevo organismo en la base de datos        
        System.out.println("va a guardar "+dbDesafio);
        Desafio savedDesafio = repository.save(dbDesafio);

        // Devuelve el organismo guardado después de convertirlo a DesafioRecord 
        return toDesafioRecord(savedDesafio);
    }

    private DesafioRecord updateDesafio(DesafioRecord laDesafio) {        
        var dbDesafio = repository.findById(laDesafio.id).orElseThrow();        
        dbDesafio.setId(generateRandomLong());      
        dbDesafio.setMateriaid(laDesafio.materiaid);        
        dbDesafio.setPracticoid(laDesafio.practicoid);        
        dbDesafio.setOrden(laDesafio.orden);        
        dbDesafio.setDesafio(laDesafio.desafio);                
                Desafio savedDesafio = repository.save(dbDesafio);
        
        return toDesafioRecord(savedDesafio);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<DesafioRecord> findAllDesafios() {
        Stream<Desafio> listaResul = repository.findAll().stream();
        
        return listaResul.map(this::toDesafioRecord).toList();
    }

    public DesafioRecord save(DesafioRecord laDesafio) {
       
        DesafioRecord rta;
        // var dbDesafio = repository.findById(elDesafio.id);
        if (!(laDesafio.id > 0))   {         
            
            rta = this.saveDesafio(laDesafio);
        }else{
            
            rta = this.updateDesafio(laDesafio);            
        }    
        return rta;
    }

  public List<DesafioRecord> findDesafioByPracticoid(Long practicoid) {        
        List<Desafio> listaResul = repository.findByPracticoid(practicoid);
       
                return listaResul.stream()
                         .map(this::toDesafioRecord)
                         .collect(Collectors.toList());
    }

}