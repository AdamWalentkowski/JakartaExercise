package com.eti.pg.task.service;

import com.eti.pg.board.repository.BoardRepository;
import com.eti.pg.task.entity.Task;
import com.eti.pg.task.repository.TaskRepository;
import com.eti.pg.user.entity.User;
import com.eti.pg.user.entity.UserRole;
import com.eti.pg.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
public class TaskService {
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private SecurityContext securityContext;

    @Inject
    public TaskService(TaskRepository taskRepository, BoardRepository boardRepository, UserRepository userRepository, SecurityContext securityContext) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    public List<Task> findTasksByBoardId(Long id) {
        return taskRepository.findByBoardId(id);
    }

    public List<Task> findTasksByBoardName(String boardName) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return taskRepository.findByBoardName(boardName);
        }
        var user = userRepository.find(securityContext.getCallerPrincipal().getName()).orElseThrow();
        return taskRepository.findByBoardNameAndUser(boardName, user);
    }

    public Optional<Task> findTaskByIdAndBoardName(Long id, String boardName) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return taskRepository.findByIdAndBoardName(id, boardName);
        }
        var user = userRepository.find(securityContext.getCallerPrincipal().getName()).orElseThrow();
        return taskRepository.findByIdAndBoardNameAndUser(id, boardName, user);
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.find(id);
    }

    public void createTask(Task task) {
        var user = userRepository.find(securityContext.getCallerPrincipal().getName()).orElseThrow();
        task.setUser(user);
        taskRepository.create(task);
        boardRepository.find(task.getBoard().getId()).ifPresent(board -> board.getTasks().add(task));
    }

    public void deleteTask(Long id) throws EJBAccessException {
        var task = taskRepository.find(id).orElseThrow();
        if (((securityContext.isCallerInRole(UserRole.DEVELOPER) ||
                securityContext.isCallerInRole(UserRole.MAINTAINER)) &&
                task.getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) ||
                securityContext.isCallerInRole(UserRole.ADMIN)) {
            task.getBoard().getTasks().remove(task);
            taskRepository.delete(taskRepository.find(id).orElseThrow());
        } else {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
    }

    public void updateTask(Task task) throws EJBAccessException {
        var original = taskRepository.find(task.getId()).orElseThrow();
        if (((securityContext.isCallerInRole(UserRole.DEVELOPER) ||
                securityContext.isCallerInRole(UserRole.MAINTAINER)) &&
                original.getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) ||
                securityContext.isCallerInRole(UserRole.ADMIN)) {
            taskRepository.detach(original);
            if (!original.getBoard().getId().equals(task.getBoard().getId())) {
                boardRepository.find(original.getBoard().getId()).ifPresent(b -> b.getTasks().removeIf(t -> t.getId().equals(task.getId())));
                boardRepository.find(task.getBoard().getId()).ifPresent(b -> b.getTasks().add(task));
            }
            taskRepository.update(task);
        } else {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
    }

    public void flushData() {
        taskRepository.flushData();
    }
}
