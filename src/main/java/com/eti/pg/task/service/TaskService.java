package com.eti.pg.task.service;

import com.eti.pg.task.entity.Task;
import com.eti.pg.task.repository.TaskRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@NoArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;

    @Inject
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

    public void createTask(Task task) {
        taskRepository.create(task);
    }

    public void deleteTask(Long id) {
        taskRepository.delete(taskRepository.find(id).orElseThrow());
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
    }
}
