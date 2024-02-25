package com.example.todomanagement.controller;

import com.example.todomanagement.dto.TaskDto;
import com.example.todomanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/api/task/")
public class TaskController {
    private TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add-task")
    public ResponseEntity<TaskDto> addTask(@RequestBody @Valid TaskDto taskDto){
        TaskDto savedTask = taskService.addTask(taskDto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping("get-task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long taskId){
        TaskDto retrievedTask = taskService.getTaskById(taskId);
        return new ResponseEntity<>(retrievedTask, HttpStatus.OK);
    }
    @GetMapping("get-tasks")
    public ResponseEntity<List<TaskDto>> getAllTask(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update-task")
    public ResponseEntity<TaskDto> updateTask(@RequestBody @Valid TaskDto taskDto){
        return new ResponseEntity<>(taskService.updateTask(taskDto), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId){
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("id") Long taskId){
        return new ResponseEntity<>(taskService.completeTask(taskId), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TaskDto> incompleteTask(@PathVariable("id") Long taskId){
        return new ResponseEntity<>(taskService.incompleteTask(taskId), HttpStatus.OK);
    }

}
