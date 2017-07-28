package com.comarch.fbi.internship.todolg.endpoints.converters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.TaskEndpoint;
import com.comarch.fbi.internship.todolg.model.entities.Group;
import com.comarch.fbi.internship.todolg.model.entities.Task;

/**
 * Test konwertera listy zadań.
 */
@RunWith(MockitoJUnitRunner.class)
public class TasksListConverterTest {

    @InjectMocks
    TaskConverter taskConverter;
    @InjectMocks
    private TasksListConverter tasksListConverter;

    @Test
    public void shouldConvertTasksList() {
        // given
        tasksListConverter.taskConverter = taskConverter;
        List<Task> tasks = new ArrayList<>();
        Task task = Mockito.mock(Task.class);
        Group group = Mockito.mock(Group.class);
        LocalDateTime date = LocalDateTime.now();
        tasks.add(task);

        // when
        Mockito.when(group.getId()).thenReturn(1L);
        Mockito.when(group.getTitle()).thenReturn("group 1");

        Mockito.when(task.getId()).thenReturn(1L);
        Mockito.when(task.getTitle()).thenReturn("task");
        Mockito.when(task.getGroup()).thenReturn(group);
        Mockito.when(task.getTitle()).thenReturn("title");
        Mockito.when(task.getNote()).thenReturn("note");
        Mockito.when(task.getPriority()).thenReturn(1);
        Mockito.when(task.getStartDate()).thenReturn(date);

        List<TaskEndpoint> convertedTasks = tasksListConverter.convert(tasks);

        // then
        Assert.assertNotNull(convertedTasks);
        Assert.assertEquals(tasks.size(), convertedTasks.size());
        Assert.assertEquals(task.getId().intValue(), convertedTasks.get(0).getId().intValue());
        Assert.assertEquals(task.getTitle(), convertedTasks.get(0).getTitle());
        Assert.assertEquals(task.getGroup().getId().intValue(),
                convertedTasks.get(0).getGroupId().intValue());
        Assert.assertEquals(task.getPriority(), convertedTasks.get(0).getPriority().intValue());
        Assert.assertEquals(task.getNote(), convertedTasks.get(0).getNote());
        Assert.assertEquals(task.getStartDate().toString(), convertedTasks.get(0).getStartDate());
    }
}
