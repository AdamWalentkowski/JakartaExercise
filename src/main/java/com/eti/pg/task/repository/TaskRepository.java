package com.eti.pg.task.repository;

import com.eti.pg.db.DataStore;
import com.eti.pg.repository.Repository;
import com.eti.pg.task.entity.Task;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class TaskRepository implements Repository<Task, Long> {
    private final DataStore dataStore;

    @Inject
    public TaskRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Task> find(Long id) {
        return dataStore.findTask(id);
    }

    @Override
    public List<Task> findAll() {
        //TODO: next exercise
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void create(Task entity) {
        dataStore.createTask(entity);
    }

    @Override
    public void delete(Task entity) {
        dataStore.deleteTask(entity.getId());
    }

    @Override
    public void update(Task entity) {
        dataStore.updateTask(entity);
    }

    public List<Task> findByBoardId(Long id) {
        return dataStore.findTasksByBoardId(id);
    }
}
