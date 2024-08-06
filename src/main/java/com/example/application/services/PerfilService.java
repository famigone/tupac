package com.example.application.services;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import com.example.application.model.Role;
import org.springframework.stereotype.Service;

import com.example.application.model.Perfil;
import com.example.application.repository.PerfilRepository;
import com.example.application.services.PerfilService.PerfilRecord;
import com.example.application.services.UserEndpoint.UserRecord;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.example.application.services.UserEndpoint;
import dev.hilla.BrowserCallable;
import jakarta.validation.constraints.NotNull;


import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
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
public class PerfilService {

    private final PerfilRepository repository;
 
    

    public record PerfilRecord(
            Long id,
            // nombre de la aplicación
            @NotNull             
            String nombre,            
            
            @NotNull             
            String apellido,

            @NotNull             
            String legajo,
            

            @NotNull             
            String password,
            
            @NotNull             
            Long userid

            ) {
    }

    public PerfilService(PerfilRepository repository) {
        this.repository = repository;
    }

    private PerfilRecord toPerfilRecord(Perfil laPerfil) {
        return new PerfilRecord(
                laPerfil.getId(),
                laPerfil.getNombre(),                 
                laPerfil.getApellido(),
                laPerfil.getLegajo(),
                laPerfil.getPassword(), 
                laPerfil.getUserid()
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

    public PerfilRecord savePerfil(PerfilRecord nuevoPerfil) {
        // Crea un nuevo objeto Perfil y asigna los valores del objeto recibido
        Perfil dbPerfil = new Perfil();
         // Crea un nuevo UserRecord con el legajo como username
        Set<Role> roles = EnumSet.of(Role.USER);
        UserRecord elUsuario = new UserRecord(
            null, 
            nuevoPerfil.legajo, 
            nuevoPerfil.nombre.toLowerCase() + " "+ nuevoPerfil.apellido.toUpperCase(), 
            nuevoPerfil.password, 
            roles
            );


        dbPerfil.setId(generateRandomLong());      
        dbPerfil.setNombre(nuevoPerfil.nombre);        
        dbPerfil.setApellido(nuevoPerfil.apellido);        
        dbPerfil.setLegajo(nuevoPerfil.legajo);        
        // Guarda el nuevo organismo en la base de datos        
        Perfil savedPerfil = repository.save(dbPerfil);

        // Devuelve el organismo guardado después de convertirlo a PerfilRecord 
        return toPerfilRecord(savedPerfil);
    }

    private PerfilRecord updatePerfil(PerfilRecord elPerfil) {
        
        var dbPerfil = repository.findById(elPerfil.id).orElseThrow();
        System.out.println("encontró a "+dbPerfil.getId());
        dbPerfil.setNombre(elPerfil.nombre);        
        dbPerfil.setApellido(elPerfil.apellido);        
        dbPerfil.setLegajo(elPerfil.legajo);        
        Perfil savedPerfil = repository.save(dbPerfil);
        
        return toPerfilRecord(savedPerfil);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<PerfilRecord> findAllPerfils() {
        Stream<Perfil> listaResul = repository.findAll().stream();
        
        return listaResul.map(this::toPerfilRecord).toList();
    }

    public Optional<Perfil> findPerfilById(Long id) {
        Optional<Perfil> resul = repository.findById(id);        
        return resul;
    }

    public PerfilRecord save(PerfilRecord laPerfil) {
       
        PerfilRecord rta;
        // var dbPerfil = repository.findById(elPerfil.id);
        if (!(laPerfil.id > 0))   {         
            System.out.println("oneeeeeeeeee");
            rta = this.savePerfil(laPerfil);
        }else{
            System.out.println("twooooooooooo");
            rta = this.updatePerfil(laPerfil);            
        }    
        return rta;
    }


}