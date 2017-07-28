package com.comarch.fbi.internship.todolg.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comarch.fbi.internship.todolg.endpoints.api.IGroupsEndpoint;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.BaseResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.GroupEndpoint;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.GroupResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.GroupsListResponse;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewGroup;
import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewGroupResponse;
import com.comarch.fbi.internship.todolg.endpoints.converters.GroupConverter;
import com.comarch.fbi.internship.todolg.endpoints.converters.GroupJsonConverter;
import com.comarch.fbi.internship.todolg.endpoints.converters.GroupsListConverter;
import com.comarch.fbi.internship.todolg.model.entities.Group;
import com.comarch.fbi.internship.todolg.services.api.IGroupService;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint grup.
 */
@RestController
@Slf4j
public class GroupsEndpoint extends BaseEndpoint implements IGroupsEndpoint {

    IGroupService groupService;

    GroupConverter groupConverter;

    GroupsListConverter groupsListConverter;

    GroupJsonConverter groupJsonConverter;

    /**
     * Konstruktor do wstrzykiwania zależności.
     *
     * @param groupService        serwis zarządzający grupami
     * @param groupConverter      konwerter Group na GroupEndpoint
     * @param groupsListConverter konwerter lisy Group na listę GroupEndpoint
     * @param groupJsonConverter  konweter jsona grupy
     */
    @Autowired
    public GroupsEndpoint(final IGroupService groupService,
            final GroupJsonConverter groupJsonConverter,
            final GroupConverter groupConverter,
            final GroupsListConverter groupsListConverter) {
        this.groupService = groupService;
        this.groupConverter = groupConverter;
        this.groupsListConverter = groupsListConverter;
        this.groupJsonConverter = groupJsonConverter;
    }

    @Override
    public ResponseEntity<BaseResponse> addTest(
            final @RequestParam(value = "count", required = false, defaultValue = "5")
                    Integer count) throws Exception {
        groupService.addTestGroups(count);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(final @PathVariable Integer id) throws Exception {
        groupService.deleteGroup(id.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupResponse> getGroup(
            final @ApiParam(value = "Id grupy") @PathVariable Integer id) throws Exception {
        GroupEndpoint group = groupConverter.convert(groupService.getGroupById(id));
        GroupResponse response = new GroupResponse();
        response.setGroup(group);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupsListResponse> getGroupsList(final @ApiParam(value = "Tytuł grupy")
            @RequestParam(value = "title", required = false) String title) throws Exception {
        List<GroupEndpoint> groupList;

        if (title == null) {
            groupList = groupsListConverter.convert(groupService.getAllGroups());
        }
        else {
            groupList = groupsListConverter.convert(groupService.getGroupByTitle(title));
        }

        GroupsListResponse response = new GroupsListResponse();
        response.setGroups(groupList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewGroupResponse> newGroup(
            final @ApiParam(value = "Obiekt nowej grupy", required = true) @RequestBody
                    NewGroup body) throws Exception {
        Group newGroup = groupJsonConverter.convert(body);
        Group group = groupService.createGroup(newGroup);

        NewGroupResponse response = new NewGroupResponse();
        response.setId(group.getId().intValue());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GroupResponse> updateGroup(final @PathVariable Integer id,
            final @ApiParam(value = "Obiekt aktualizowanej grupy", required = true) @RequestBody
                    NewGroup body) throws Exception {
        Group newGroup = groupJsonConverter.convert(body);
        Group group = groupService.updateGroup(id.longValue(), newGroup);

        GroupResponse response = new GroupResponse();
        response.setGroup(groupConverter.convert(group));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}