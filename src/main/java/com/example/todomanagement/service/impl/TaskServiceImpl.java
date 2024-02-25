package com.example.todomanagement.service.impl;

import com.example.todomanagement.dto.TaskDto;
import com.example.todomanagement.entity.Task;
import com.example.todomanagement.exception.ResourceNotFoundException;
import com.example.todomanagement.repository.TaskRepository;
import com.example.todomanagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;
    @Override
    public TaskDto addTask(TaskDto taskDto) {
        Task savedTask = taskRepository.save(modelMapper.map(taskDto, Task.class));
        return modelMapper.map(savedTask, TaskDto.class) ;
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        Task task= taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("TASK", "ID", taskId)
        );
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks() {
    List<Task> taskList = taskRepository.findAll();
    List<TaskDto> taskDtos = new ArrayList<>();
        for(Task task : taskList){
            taskDtos.add(modelMapper.map(task, TaskDto.class));
        }
        return taskDtos;
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        Task retrievedTask = taskRepository.findById(taskDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("TASK", "ID", taskDto.getId())
        );

        retrievedTask.setTitle(taskDto.getTitle());
        retrievedTask.setDescription(taskDto.getDescription());
        retrievedTask.setCompleted(taskDto.getCompleted());

        Task savedTask = taskRepository.save(retrievedTask);
        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public String deleteTask(Long taskId) {
        Task retrievedTask = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("TASK", "ID", taskId)
        );
        taskRepository.deleteById(taskId);

        return "Deleted Task Successfully!";
    }

    @Override
    public TaskDto completeTask(Long taskId) {
        Task retrievedTask = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("TASK", "ID", taskId)
        );
        retrievedTask.setCompleted(true);
        Task savedTask = taskRepository.save(retrievedTask);
        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public TaskDto incompleteTask(Long taskId) {
        Task retrievedTask = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("TASK", "ID", taskId)
        );
        retrievedTask.setCompleted(false);
        Task savedTask = taskRepository.save(retrievedTask);
        return modelMapper.map(savedTask, TaskDto.class);
    }
}
