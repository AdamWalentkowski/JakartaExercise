package com.eti.pg.task.service;

import com.eti.pg.task.entity.Task;
import com.eti.pg.task.repository.TaskRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

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

    public void create(Task task) {
        taskRepository.create(task);
    }

    public void deleteTask(Long id) {
        log.error("jestem w metodzie deleteTask");
        taskRepository.delete(taskRepository.find(id).orElseThrow());
    }
}
