package com.eti.pg.config;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.Role;
import com.eti.pg.board.entity.Board;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.entity.Task;
import com.eti.pg.task.service.TaskService;
import com.eti.pg.user.entity.User;
import com.eti.pg.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;

@ApplicationScoped
public class InitializedData {
    private final UserService userService;
    private final BoardService boardService;
    private final TaskService taskService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService,
                           BoardService boardService,
                           TaskService taskService,
                           RequestContextController requestContextController) {
        this.userService = userService;
        this.boardService = boardService;
        this.taskService = taskService;
        this.requestContextController = requestContextController;
    }

    @Transactional
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        requestContextController.activate();

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

        initUsers.forEach(userService::createUser);
        initBoards.forEach(boardService::createBoard);
        boardService.flushData();

        var initTasks = Arrays.asList(
                Task.builder()
                        .title("Zrób to")
                        .description("coś tam coś tam")
                        .priority(5)
                        .creationDate(LocalDate.now())
                        .board(boardService.findBoardById(1L).orElseThrow())
//                        .user(null)
                        .build(),
                Task.builder()
                        .title("Zrób tamto")
                        .description("bla bla bla")
                        .priority(3)
                        .creationDate(LocalDate.now())
                        .board(boardService.findBoardById(1L).orElseThrow())
//                        .user(null)
                        .build(),
                Task.builder()
                        .title("Zrób jeszcze to")
                        .description("xyz xyz")
                        .priority(2)
                        .creationDate(LocalDate.now())
                        .board(boardService.findBoardById(2L).orElseThrow())
//                        .user(null)
                        .build()
        );
        initTasks.forEach(taskService::createTask);
        boardService.flushData();


        initBoards.forEach(x -> {
            System.out.println("Board " + x.getTitle() + "tasks: ");
            x.getTasks().forEach(y -> System.out.println(y.toString()));
        });

        initTasks.forEach(x -> {
            System.out.println("Task " + x.getTitle() + "boards: ");
            x.getBoard().getTasks().forEach(y -> System.out.println(y.toString()));
        });

        requestContextController.deactivate();
    }
}