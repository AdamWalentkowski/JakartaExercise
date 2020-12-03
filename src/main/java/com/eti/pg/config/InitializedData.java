package com.eti.pg.config;

import com.eti.pg.board.BoardScope;
import com.eti.pg.user.entity.UserRole;
import com.eti.pg.board.entity.Board;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.entity.Task;
import com.eti.pg.task.service.TaskService;
import com.eti.pg.user.entity.User;
import com.eti.pg.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.time.LocalDate;
import java.util.Arrays;

@Singleton
@Startup
public class InitializedData {
    private EntityManager em;
    private Pbkdf2PasswordHash pbkdf;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Inject
    public InitializedData(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    public InitializedData() {
    }

    @PostConstruct
    private synchronized void init() {
        var initUsers = Arrays.asList(
            User.builder()
                    .login("admin")
                    .password(pbkdf.generate("admin".toCharArray()))
                    .firstName("Marek")
                    .lastName("Niewazne")
                    .employmentDate(LocalDate.now().minusYears(5))
                    .role(UserRole.ADMIN)
                    .build(),
            User.builder()
                    .login("maintainer")
                    .password(pbkdf.generate("maintainer".toCharArray()))
                    .firstName("Kornel")
                    .lastName("Krzywiec")
                    .employmentDate(LocalDate.now().minusYears(3))
                    .role(UserRole.MAINTAINER)
                    .build(),
            User.builder()
                    .login("dev1")
                    .password(pbkdf.generate("dev1".toCharArray()))
                    .firstName("Maciek")
                    .lastName("Keicam")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(UserRole.DEVELOPER)
                    .build(),
            User.builder()
                    .login("dev2")
                    .password(pbkdf.generate("dev2".toCharArray()))
                    .firstName("Bartek")
                    .lastName("Nudny")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(UserRole.DEVELOPER)
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
                        .title("Zrób zad1")
                        .description("coś tam coś tam")
                        .priority(5)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(0))
                        .user(initUsers.get(0))
                        .build(),
                Task.builder()
                        .title("Zrób zad2")
                        .description("bla bla bla")
                        .priority(3)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(0))
                        .user(initUsers.get(1))
                        .build(),
                Task.builder()
                        .title("Zrób zad3")
                        .description("xyz xyz")
                        .priority(2)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(1))
                        .user(initUsers.get(2))
                        .build(),
                Task.builder()
                        .title("Zrób zad4")
                        .description("fafafafafa")
                        .priority(20)
                        .creationDate(LocalDate.now())
                        .board(initBoards.get(2))
                        .user(initUsers.get(3))
                        .build()
        );
        initTasks.forEach(em::persist);

        initUsers.forEach(x -> System.out.println("User " + x.getLogin()));

        initBoards.forEach(x -> {
            System.out.println("Board " + x.getTitle() + "tasks: ");
            x.getTasks().forEach(y -> System.out.println(y.toString()));
        });

        initTasks.forEach(x -> {
            System.out.println("Task " + x.getTitle() + "boards: ");
            x.getBoard().getTasks().forEach(y -> System.out.println(y.toString()));
        });
    }
}