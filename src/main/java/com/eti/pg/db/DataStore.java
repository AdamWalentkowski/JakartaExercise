package com.eti.pg.db;

import com.eti.pg.board.entity.Board;
import com.eti.pg.serialization.CloningUtility;
import com.eti.pg.task.entity.Task;
import com.eti.pg.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final Set<Board> boards = new HashSet<>();
    private final Set<Task> tasks = new HashSet<>();

//USER METHODS
    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<User> findUser(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<User> findAllUsers() {
        return users.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(user);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", user.getId()));
                });
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> {
                    user.setId(findAllUsers().stream().mapToLong(User::getId).max().orElse(0) + 1);
                    users.add(user);
                });
    }

//BOARD METHODS
    public synchronized Optional<Board> findBoard(Long id) {
        return boards.stream()
                .filter(board -> board.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<Board> findBoard(String title) {
        return boards.stream()
                .filter(board -> board.getTitle().equals(title))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public List<Board> findAllBoards() {
        return boards.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public void createBoard(Board board) {
        findBoard(board.getTitle()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The board title \"%s\" is not unique", board.getTitle()));
                },
                () -> {
                    board.setId(findAllBoards().stream().mapToLong(Board::getId).max().orElse(0) + 1);
                    boards.add(board);
                });
    }

    public synchronized void deleteBoard(Long id) {
        findBoard(id).ifPresentOrElse(
                boards::remove,
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The table with id \"%d\" does not exist", id));
                });
    }

    public void updateBoard(Board board) {
        findBoard(board.getId()).ifPresentOrElse(
                original -> {
                    boards.remove(original);
                    boards.add(board);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The task with id \"%d\" does not exist", board.getId()));
                });
    }

//TASK METHODS
    public synchronized Optional<Task> findTask(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<Task> findTask(String taskTitle, String boardTitle) {
        return tasks.stream()
                .filter(task -> task.getTitle().equals(taskTitle) &&
                        task.getBoard().getTitle().equals(boardTitle))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<Task> findTask(Long id, String boardTitle) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id) &&
                        task.getBoard().getTitle().equals(boardTitle))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Task> findAllTasks() {
        return tasks.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized void createTask(Task task) throws IllegalArgumentException {
        findTask(task.getTitle(), task.getBoard().getTitle()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The task title \"%s\" is not unique for board \"%s\"",
                                    task.getTitle(), task.getBoard().getTitle()));
                },
                () -> {
                    task.setId(findAllTasks().stream().mapToLong(Task::getId).max().orElse(0) + 1);
                    tasks.add(task);
                });
    }

    public void deleteTask(Long id) {
        findTask(id).ifPresentOrElse(
                tasks::remove,
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The task with id \"%d\" does not exist", id));
                });
    }

    public void updateTask(Task task) {
        findTask(task.getId()).ifPresentOrElse(
                original -> {
                    tasks.remove(original);
                    tasks.add(task);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The task with id \"%d\" does not exist", task.getId()));
                });
    }
    public List<Task> findTasksByBoardId(Long id) {
        return findBoard(id)
                .map(board -> tasks.stream()
                        .filter(task -> task.getBoard().getTitle().equals(board.getTitle()))
                        .map(CloningUtility::clone)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("The board with id \"%d\" does not exist", id)));
    }

    public List<Task> findTasksByBoardName(String boardName) {
        return findBoard(boardName)
                .map(board -> tasks.stream()
                        .filter(task -> task.getBoard().getTitle().equals(board.getTitle()))
                        .map(CloningUtility::clone)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("The board with id \"%s\" does not exist", boardName)));
    }
}