package com.distribuida.service;

import com.distribuida.db.Persona;

import java.util.List;

public interface IServicioPersona {
    public void insertar(Persona p);

    public void eliminar(Integer id);

    public void actualizar(Persona p);

    public Persona buscarPersona(int id);

    public List<Persona> buscarTodos();
}
