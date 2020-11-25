package com.eti.pg.user.repository;

import com.eti.pg.board.entity.Board;
import com.eti.pg.repository.Repository;
import com.eti.pg.user.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepository implements Repository<User, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Optional<User> find(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
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
        em.remove(em.find(Board.class, entity.getId()));
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
