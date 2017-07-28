package com.comarch.fbi.internship.todolg.endpoints.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewTask;
import com.comarch.fbi.internship.todolg.model.common.RestBadRequestException;
import com.comarch.fbi.internship.todolg.model.entities.Task;
import com.comarch.fbi.internship.todolg.services.api.IGroupService;

/**
 * Konwerter jsona zadań.
 */
@Component
public class TaskJsonConverter implements IConverter<NewTask, Task> {

    private IGroupService groupService;

    /**
     * Konstruktor.
     *
     * @param groupService seriws dla grup
     */
    @Autowired
    TaskJsonConverter(final IGroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public Task convert(final NewTask body) {
        Task task = new Task();
        task.setGroup(groupService.getGroupById(body.getGroupId()));

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            LocalDateTime date = LocalDateTime.parse(body.getStartDate(), format);
            task.setStatus(body.getStatus());
            task.setPriority(body.getPriority());
            task.setNote(body.getNote());
            task.setStartDate(date);
            task.setTitle(body.getTitle());

        }
        catch (final Exception e) {
            throw new RestBadRequestException("Error parsing json");
        }

        return task;
    }
}