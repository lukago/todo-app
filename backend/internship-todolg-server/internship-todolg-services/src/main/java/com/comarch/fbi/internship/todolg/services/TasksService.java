package com.comarch.fbi.internship.todolg.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.comarch.fbi.internship.todolg.model.common.RestNotFoundException;
import com.comarch.fbi.internship.todolg.model.entities.QTask;
import com.comarch.fbi.internship.todolg.model.entities.Task;
import com.comarch.fbi.internship.todolg.model.repositories.TasksRepository;
import com.comarch.fbi.internship.todolg.services.api.ITaskService;

import lombok.extern.slf4j.Slf4j;


/**
 * Serwis do obsługi zadań.
 */
@Component
@Slf4j
@Validated
public class TasksService implements ITaskService {

    private TasksRepository tasksRepository;

    private GroupsService groupsService;

    /**
     * Konstruktor.
     *
     * @param tasksRepository repozytorium zadań
     * @param groupsService serwis dla grup
     */
    @Autowired
    public TasksService(final TasksRepository tasksRepository, final GroupsService groupsService) {
        this.tasksRepository = tasksRepository;
        this.groupsService = groupsService;
    }

    @Override
    @Transactional
    public void addTestTasks(final Integer groupId, final Integer count) {
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Task task = new Task();
            task.setTitle("test title");
            task.setStartDate(LocalDateTime.now().plusDays(i));
            task.setNote("test note " + i);
            task.setGroup(groupsService.getGroupById(groupId));
            task.setPriority(i);
            task.setStatus(false);
            tasks.add(task);
        }

        tasksRepository.save(tasks);
    }

    @Override
    public List<Task> getAllTasks() {
        return tasksRepository.findAll();
    }

    @Override
    public List<Task> getTasksByGroup(final Integer groupId) {
        return new ArrayList<>(groupsService.getGroupById(groupId).getTasks());
    }

    @Override
    public Task getTaskById(final Integer id) {
        Task task = tasksRepository.findOne(QTask.task.id.eq(id.longValue()));

        if (task == null) {
            throw new RestNotFoundException("No task with given id: " + id);
        }

        return task;
    }

    @Override
    @Transactional
    public Task createTask(final @Valid Task task) {
        return tasksRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(final Long id, final @Valid Task taskUpdated) {
        Task task = tasksRepository.findOne(id);

        if (task == null) {
            throw new RestNotFoundException("No task with given id: " + id);
        }

        task.setTitle(taskUpdated.getTitle());
        task.setStartDate(taskUpdated.getStartDate());
        task.setGroup(taskUpdated.getGroup());
        task.setPriority(taskUpdated.getPriority());
        task.setNote(taskUpdated.getNote());
        task.setStatus(taskUpdated.isStatus());
        task.setId(id);

        return tasksRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(final Long id) {
        if (tasksRepository.exists(id)) {
            tasksRepository.delete(id);
        }
    }

    @Override
    public List<Task> getTasksByTitle(final String title) {
        List<Task> tasks = tasksRepository
                .findAll(QTask.task.title.likeIgnoreCase("%" + title + "%"));

        if (tasks == null) {
            return new ArrayList<>();
        }

        return tasks;
    }
}