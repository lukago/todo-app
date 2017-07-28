package com.comarch.fbi.internship.todolg.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.comarch.fbi.internship.todolg.model.entities.Group;
import com.comarch.fbi.internship.todolg.model.repositories.GroupsRepository;

/**
 * Testy dla serwisu {@link GroupsService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupsServiceTest {

    @InjectMocks
    GroupsService groupsService;

    @Mock
    GroupsRepository groupsRepository;

    @Test
    public void shouldSaveTestGroups() {
        // given
        int groupsCount = 10;

        // when
        Mockito.when(groupsRepository.save(Mockito.any(Group.class))).thenReturn(new Group());
        groupsService.addTestGroups(groupsCount);

        // then
        Mockito.verify(groupsRepository, Mockito.times(1))
                .save(Mockito.anyCollectionOf(Group.class));
    }

    @Test
    public void shouldCreateGroup() {
        // given
        Group group = new Group();
        group.setTitle("title");

        // when
        Mockito.when(groupsRepository.save(Mockito.any(Group.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Group createdGroup = groupsService.createGroup(group);

        // then
        Assert.assertEquals(group.getTitle(), createdGroup.getTitle());
    }
}