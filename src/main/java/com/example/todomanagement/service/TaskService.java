package com.example.todomanagement.service;

import com.example.todomanagement.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto addTask(TaskDto taskDto);
    TaskDto getTaskById(Long taskId);
    List<TaskDto> getAllTasks();
    TaskDto updateTask(TaskDto taskDto);
    String deleteTask(Long taskId);
    TaskDto completeTask(Long taskId);
    TaskDto incompleteTask(Long taskId);
}
