package com.eti.pg.user.repository;

import com.eti.pg.db.DataStore;
import com.eti.pg.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, Long> {
    private final DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<User> find(Long id) {
        return dataStore.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    @Override
    public void create(User entity) {
        dataStore.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        //TODO: next exercise
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void update(User entity) {
        dataStore.updateUser(entity);
    }
}
