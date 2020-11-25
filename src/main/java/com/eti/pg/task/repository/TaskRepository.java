package com.eti.pg.task.repository;

import com.eti.pg.repository.Repository;
import com.eti.pg.task.entity.Task;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class TaskRepository implements Repository<Task, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Task> find(Long id) {
        return Optional.ofNullable(em.find(Task.class, id));
    }

    @Override
    public List<Task> findAll() {
        return em.createQuery("select t from Task t", Task.class).getResultList();
    }

    @Override
    public void create(Task entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Task entity) {
        em.remove(em.find(Task.class, entity.getId()));
    }

    @Override
    public void update(Task entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Task entity) {
        em.detach(entity);
    }

    public List<Task> findByBoardId(Long id) {
        return em.createQuery("select t from Task t where t.board.id = :id", Task.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Task> findByBoardName(String boardName) {
        return em.createQuery("select t from Task t where t.board.title = :boardName", Task.class)
                .setParameter("boardName", boardName)
                .getResultList();
    }

    public Optional<Task> findByIdAndBoardName(Long id, String boardName) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select t from Task t where t.id = :id and t.board.title = :boardName", Task.class)
                            .setParameter("id", id)
                            .setParameter("boardName", boardName)
                            .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
