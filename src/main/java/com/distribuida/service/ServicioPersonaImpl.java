package com.distribuida.service;

import com.distribuida.db.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioPersonaImpl implements IServicioPersona {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insertar(Persona p) {
        em.persist(p);
    }
    @Override
    public void eliminar(Integer id) {
        em.remove(buscarPersona(id));
    }
    @Override
    public void actualizar(Persona p) {
        em.merge(p);
    }
    @Override
    public Persona buscarPersona(int id) {
        return em.find(Persona.class, id);

    }
    @Override
    public List<Persona> buscarTodos() {
        TypedQuery<Persona>myQ=em.createQuery("SELECT p FROM Persona p ORDER BY p.id ASC", Persona.class);
        return myQ.getResultList();
    }

}
