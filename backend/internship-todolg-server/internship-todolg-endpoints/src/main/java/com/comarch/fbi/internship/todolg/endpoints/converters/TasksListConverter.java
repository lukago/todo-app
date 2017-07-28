package com.comarch.fbi.internship.todolg.endpoints.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.TaskEndpoint;
import com.comarch.fbi.internship.todolg.model.entities.Task;

/**
 * Konwerter pomiędzy wygernerowanym Task w endpoint a Task w modelu.
 */
@Component
public class TasksListConverter implements IConverter<List<Task>, List<TaskEndpoint>> {

    TaskConverter taskConverter;

    /**
     * Konstruktor.
     *
     * @param taskConverter konwerter zadań
     */
    @Autowired
    public TasksListConverter(final TaskConverter taskConverter) {
        this.taskConverter = taskConverter;
    }

    @Override
    public List<TaskEndpoint> convert(final List<Task> tasks) {
        List<TaskEndpoint> result = new ArrayList<>();

        if (tasks == null || tasks.isEmpty()) {
            return result;
        }

        for (Task model : tasks) {
            result.add(taskConverter.convert(model));
        }

        return result;
    }
}