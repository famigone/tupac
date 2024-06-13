package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table
public class User implements Serializable {
/*
    @ManyToMany
    private List<Practico> practicos = new LinkedList<>();

    @ManyToMany
    private List<Desafio> desafios = new LinkedList<>();


    @ManyToMany
    private List<Materia> materias = new LinkedList<>();


    public List<Materia> getMaterias() {
        return materias;
    }
    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    */
    @PrimaryKey
    private Long id;
    private String username;
    private String name;
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonIgnore
    private String hashedPassword;    
    
    private Set<Role> roles;

    
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    

}
