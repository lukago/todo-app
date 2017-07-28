package com.comarch.fbi.internship.todolg.endpoints.converters;

import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.TaskEndpoint;
import com.comarch.fbi.internship.todolg.model.entities.Task;


/**
 * Konwerter zadania z modelu na endpoint.
 */
@Component
public class TaskConverter implements IConverter<Task, TaskEndpoint> {
    @Override
    public TaskEndpoint convert(final Task model) {
        TaskEndpoint task = new TaskEndpoint();

        if (model == null) {
            return task;
        }

        task.setId(model.getId().intValue());
        task.setTitle(model.getTitle());
        task.setNote(model.getNote());
        task.setStartDate(model.getStartDate().toString());
        task.setGroupId(model.getGroup().getId().intValue());
        task.setPriority(model.getPriority());
        task.setStatus(model.isStatus());

        return task;
    }
}