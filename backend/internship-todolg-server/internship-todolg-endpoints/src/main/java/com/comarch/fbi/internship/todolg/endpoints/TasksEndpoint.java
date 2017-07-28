package com.comarch.fbi.internship.todolg.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comarch.fbi.internship.todolg.endpoints.api.ITasksEndpoint;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.BaseResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewTask;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewTaskResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.TaskEndpoint;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.TaskResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.TasksListResponse;
import com.comarch.fbi.internship.todolg.endpoints.converters.TaskConverter;
import com.comarch.fbi.internship.todolg.endpoints.converters.TaskJsonConverter;
import com.comarch.fbi.internship.todolg.endpoints.converters.TasksListConverter;
import com.comarch.fbi.internship.todolg.model.entities.Task;
import com.comarch.fbi.internship.todolg.services.api.ITaskService;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint dla zadań.
 */
@RestController
@Slf4j
public class TasksEndpoint extends BaseEndpoint implements ITasksEndpoint {

    private final TasksListConverter tasksListConverter;

    private final TaskConverter taskConverter;

    private final TaskJsonConverter taskJsonConverter;

    private final ITaskService taskService;

    /**
     * Konstruktor do wstrzykiawnia zależności.
     *
     * @param tasksListConverter konwerter list zadań
     * @param taskJsonConverter  konwerter jsona na zadanie
     * @param taskConverter      konweter zadań
     * @param taskService        serwis obsługujący zadania
     */
    @Autowired
    public TasksEndpoint(final TasksListConverter tasksListConverter,
            final TaskJsonConverter taskJsonConverter, final TaskConverter taskConverter,
            final ITaskService taskService) {
        this.tasksListConverter = tasksListConverter;
        this.taskJsonConverter = taskJsonConverter;
        this.taskConverter = taskConverter;
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<BaseResponse> addTest(
            final @ApiParam(value = "Id grupy zadania", required = true)
            @RequestParam(value = "groupId") Integer groupId,
            final @ApiParam(value = "Liczba zadań")
            @RequestParam(value = "count", required = false, defaultValue = "5") Integer count)
            throws Exception {

        taskService.addTestTasks(groupId, count);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(final @PathVariable Integer id) throws Exception {
        taskService.deleteTask(id.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskResponse> getTask(
            final @ApiParam(value = "Id zadania") @PathVariable Integer id) throws Exception {
        TaskEndpoint task = taskConverter.convert(taskService.getTaskById(id));
        TaskResponse response = new TaskResponse();
        response.setTask(task);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TasksListResponse> getTasksList(final @ApiParam(value = "Id grupy zadań")
            @RequestParam(value = "groupId", required = false) Integer groupId,
            final @ApiParam(value = "Tytuł zadania")
            @RequestParam(value = "title", required = false) String title) throws Exception {
        List<TaskEndpoint> tasks;

        if (groupId == null && title == null) {
            tasks = tasksListConverter.convert(taskService.getAllTasks());
        }
        else if (title == null) {
            tasks = tasksListConverter.convert(taskService.getTasksByGroup(groupId));
        }
        else if (groupId == null) {
            tasks = tasksListConverter.convert(taskService.getTasksByTitle(title));
        }
        else {
            // todo:
            tasks = tasksListConverter.convert(taskService.getAllTasks());
        }

        TasksListResponse response = new TasksListResponse();
        response.setTasks(tasks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewTaskResponse> newTask(
            final @ApiParam(value = "Obiekt nowego zadania", required = true) @RequestBody
                    NewTask body) throws Exception {
        Task newTask = taskJsonConverter.convert(body);
        Task task = taskService.createTask(newTask);

        NewTaskResponse response = new NewTaskResponse();
        response.setId(task.getId().intValue());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(final @PathVariable Integer id,
            final @ApiParam(value = "Obiekt nowego zadania", required = true) @RequestBody
                    NewTask body) throws Exception {
        Task taskUpdated = taskJsonConverter.convert(body);
        Task task = taskService.updateTask(id.longValue(), taskUpdated);

        TaskResponse response = new TaskResponse();
        response.setTask(taskConverter.convert(task));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}