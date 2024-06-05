package com.example.application.repository;

import com.example.application.model.Materia;
import java.util.List;

public interface MateriaQueryRepository {

    

    List<Materia> getAll();

    List<Materia> getMateriaByNombre(String name);

    Materia getOneMateriaByNombre(String name);

    List<Materia> getMateriaByNombreLike(String name);

    Materia getSingleMateriaByNombre(String superName);

    

    

}
