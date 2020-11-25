package com.eti.pg.board.repository;

import com.eti.pg.board.entity.Board;
import com.eti.pg.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class BoardRepository implements Repository<Board, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Board> find(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    @Override
    public void create(Board entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Board entity) {
        em.remove(em.find(Board.class, entity.getId()));
    }

    @Override
    public void update(Board entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Board entity) {
        em.detach(entity);
    }

    public Optional<Board> find(String boardName) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select b from Board b where b.title = :boardName", Board.class)
                            .setParameter("boardName", boardName)
                            .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
