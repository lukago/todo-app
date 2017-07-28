package com.comarch.fbi.internship.todolg.endpoints.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.GroupEndpoint;
import com.comarch.fbi.internship.todolg.model.entities.Group;


/**
 * Konwerter pomiędzy wygernerowanym Group w endpoint a Group w modelu.
 */
@Component
public class GroupsListConverter implements IConverter<List<Group>, List<GroupEndpoint>> {

    GroupConverter groupConverter;

    /**
     * Konstruktor klasy.
     *
     * @param groupConverter konwerter grup
     */
    @Autowired
    public GroupsListConverter(final GroupConverter groupConverter) {
        this.groupConverter = groupConverter;
    }

    @Override
    public List<GroupEndpoint> convert(final List<Group> groups) {
        List<GroupEndpoint> result = new ArrayList<>();

        if (groups == null || groups.isEmpty()) {
            return result;
        }

        for (Group model : groups) {
            result.add(groupConverter.convert(model));
        }

        return result;
    }
}