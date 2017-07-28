package com.comarch.fbi.internship.todolg.services.api;

import java.util.List;

import javax.validation.Valid;

import com.comarch.fbi.internship.todolg.model.entities.Task;

/**
 * Intefrejs serwisu zarządzającego zadaniami.
 */
public interface ITaskService {

    /**
     * Tworzy testowe zadania.
     *
     * @param groupId id grupy
     * @param count   liczba zadań
     */
    void addTestTasks(Integer groupId, Integer count);


    /**
     * Pobiera listę wszystkich zadań.
     *
     * @return Lista zadań.
     */
    List<Task> getAllTasks();

    /**
     * Pobiera listę zadań należących do grupy.
     *
     * @param groupId id grupy zadania.
     *
     * @return Lista zadań grupy.
     */
    List<Task> getTasksByGroup(Integer groupId);

    /**
     * Pobiera zadanie przez jego id.
     *
     * @param id numer zadania.
     *
     * @return Zadanie o podanym id.
     */
    Task getTaskById(Integer id);

    /**
     * Zapisuje zadanie do bazy.
     *
     * @param task zadanie do zapisania
     *
     * @return nowe zadanie
     */
    Task createTask(@Valid Task task);

    /**
     * Aktualizuje zadanie.
     *
     * @param id          numer zadania do aktualizacji
     * @param taskUpdated zaaktualizowanie zadanie.
     *
     * @return nowe zadanie
     */
    Task updateTask(Long id, @Valid Task taskUpdated);


    /**
     * Usuwa zadanie.
     *
     * @param id numer zadania
     */
    void deleteTask(Long id);

    /**
     * Pobiera listę zadań należących pasujących do tytułu.
     * Wystarczy tylko fragment.
     *
     * @param title częśc tutułu zadania.
     *
     * @return Lista zadań grupy.
     */
    List<Task> getTasksByTitle(String title);
}