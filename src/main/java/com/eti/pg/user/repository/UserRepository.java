package com.eti.pg.user.repository;

import com.eti.pg.repository.Repository;
import com.eti.pg.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, String> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Optional<User> find(String login) {
        return Optional.ofNullable(em.find(User.class, login));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getLogin()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }
}
