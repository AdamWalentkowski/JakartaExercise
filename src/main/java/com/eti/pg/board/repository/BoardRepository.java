package com.eti.pg.board.repository;

import com.eti.pg.board.entity.Board;
import com.eti.pg.db.DataStore;
import com.eti.pg.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class BoardRepository implements Repository<Board, Long> {
    private final DataStore dataStore;

    @Inject
    public BoardRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Board> find(Long id) {
        return dataStore.findBoard(id);
    }

    @Override
    public List<Board> findAll() {
        return dataStore.findAllBoards();
    }

    @Override
    public void create(Board entity) {
        dataStore.createBoard(entity);
    }

    @Override
    public void delete(Board entity) {
        dataStore.deleteBoard(entity.getId());
    }

    @Override
    public void update(Board entity) {
        //TODO: next exercise
        throw new UnsupportedOperationException("Operation not implemented.");
    }
}
