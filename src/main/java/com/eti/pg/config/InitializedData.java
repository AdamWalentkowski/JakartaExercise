package com.eti.pg.config;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.Role;
import com.eti.pg.board.entity.Board;
import com.eti.pg.task.entity.Task;
import com.eti.pg.user.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Arrays;

@Singleton
@Startup
public class InitializedData {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    public InitializedData() {
    }

    @PostConstruct
    private synchronized void init() {
        var initUsers = Arrays.asList(
            User.builder()
                    .login("lordofon1X1")
                    .firstName("Marek")
                    .lastName("Niewazne")
                    .employmentDate(LocalDate.now().minusYears(5))
                    .role(Role.ADMIN)
                    .build(),
            User.builder()
                    .login("siaBaDaBa")
                    .firstName("Kornel")
                    .lastName("Krzywiec")
                    .employmentDate(LocalDate.now().minusYears(3))
                    .role(Role.MAINTAINER)
                    .build(),
            User.builder()
                    .login("toto_Africa")
                    .firstName("Maciek")
                    .lastName("Keicam")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(Role.DEVELOPER)
                    .build(),
            User.builder()
                    .login("bartek3212_stazysta")
                    .firstName("Bartek")
                    .lastName("Nudny")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(Role.DEVELOPER)
                    .build()
        );
        initUsers.forEach(em::persist);

        var initBoards = Arrays.asList(
            Board.builder()
                    .title("Game")
                    .boardScope(BoardScope.GROUP)
                    .isPrivate(false)
                    .build(),
            Board.builder()
                    .title("GameComponent1")
                    .boardScope(BoardScope.PROJECT)
                    .isPrivate(false)
                    .build(),
            Board.builder()
                    .title("GameComponent2")
                    .boardScope(BoardScope.PROJECT)
                    .isPrivate(false)
                    .build()
        );
        initBoards.forEach(em::persist);

        var initTasks = Arrays.asList(
                Task.builder()
                        .title("Zrób to")
                        .description("coś tam coś tam")
                        .priority(5)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(0))
                        .user(initUsers.get(0))
                        .build(),
                Task.builder()
                        .title("Zrób tamto")
                        .description("bla bla bla")
                        .priority(3)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(0))
                        .user(initUsers.get(1))
                        .build(),
                Task.builder()
                        .title("Zrób jeszcze to")
                        .description("xyz xyz")
                        .priority(2)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(1))
                        .user(initUsers.get(2))
                        .build()
        );
        initTasks.forEach(em::persist);
    }
}