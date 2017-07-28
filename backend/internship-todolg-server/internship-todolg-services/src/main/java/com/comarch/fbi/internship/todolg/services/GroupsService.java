package com.comarch.fbi.internship.todolg.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.comarch.fbi.internship.todolg.model.common.RestNotFoundException;
import com.comarch.fbi.internship.todolg.model.entities.Group;
import com.comarch.fbi.internship.todolg.model.entities.QGroup;
import com.comarch.fbi.internship.todolg.model.repositories.GroupsRepository;
import com.comarch.fbi.internship.todolg.services.api.IGroupService;

import lombok.extern.slf4j.Slf4j;

/**
 * Serwis do obsługi grup.
 */
@Component
@Slf4j
@Validated
public class GroupsService implements IGroupService {

    private GroupsRepository groupsRepository;

    /**
     * Konstruktor.
     *
     * @param groupsRepository repozytorium zarządzające zadaniami
     */
    @Autowired
    public GroupsService(final GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    @Transactional
    public void addTestGroups(final Integer count) {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Group group = new Group();
            group.setTitle("test title " + i);
            groups.add(group);
        }

        groupsRepository.save(groups);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupsRepository.findAll(QGroup.group.isNotNull());
    }

    @Override
    public Group getGroupById(final Integer id) {
        Group group = groupsRepository.findOne(QGroup.group.id.eq(id.longValue()));

        if (group == null) {
            throw new RestNotFoundException("No group with id: " + id);
        }

        return group;
    }

    @Override
    @Transactional
    public Group createGroup(final @Valid Group group) {
        return groupsRepository.save(group);
    }

    @Override
    @Transactional
    public Group updateGroup(final Long id, final @Valid Group groupUpdated) {
        Group group = groupsRepository.findOne(id);

        if (group == null) {
            throw new RestNotFoundException("No group with id: " + id);
        }

        group.setTitle(groupUpdated.getTitle());

        return groupsRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteGroup(final Long id) {
        if (groupsRepository.exists(id)) {
            groupsRepository.delete(id);
        }
    }

    @Override
    public List<Group> getGroupByTitle(final String title) {
        List<Group> groups = groupsRepository
                .findAll(QGroup.group.title.likeIgnoreCase("%" + title + "%"));

        if (groups == null) {
            return new ArrayList<>();
        }

        return groups;
    }
}