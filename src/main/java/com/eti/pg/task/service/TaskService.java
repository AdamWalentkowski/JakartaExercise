package com.eti.pg.task.service;

import com.eti.pg.board.repository.BoardRepository;
import com.eti.pg.task.entity.Task;
import com.eti.pg.task.repository.TaskRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@NoArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;

    @Inject
    public TaskService(TaskRepository taskRepository, BoardRepository boardRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    public List<Task> findTasksByBoardId(Long id) {
        return taskRepository.findByBoardId(id);
    }

    public List<Task> findTasksByBoardName(String boardName) {
        return taskRepository.findByBoardName(boardName);
    }

    public Optional<Task> findTaskByIdAndBoardName(Long id, String boardName) {
        return taskRepository.findByIdAndBoardName(id, boardName);
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.find(id);
    }

    @Transactional
    public void createTask(Task task) {
        taskRepository.create(task);
        boardRepository.find(task.getBoard().getId()).ifPresent(board -> board.getTasks().add(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        var task = taskRepository.find(id).orElseThrow();
        System.out.println("taskDelete: " + task.toString());
        task.getBoard().getTasks().remove(task);
        taskRepository.delete(taskRepository.find(id).orElseThrow());
    }

    @Transactional
    public void updateTask(Task task) {
        var original = taskRepository.find(task.getId()).orElseThrow();
        taskRepository.detach(original);
        if (!original.getBoard().getId().equals(task.getBoard().getId())) {
            boardRepository.find(original.getBoard().getId()).ifPresent(b -> b.getTasks().removeIf(t -> t.getId().equals(task.getId())));
            boardRepository.find(task.getBoard().getId()).ifPresent(b -> b.getTasks().add(task));
        }
        taskRepository.update(task);
    }

    public void flushData() {
        taskRepository.flushData();
    }
}
