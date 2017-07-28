package com.comarch.fbi.internship.todolg.endpoints.converters;

import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.NewGroup;
import com.comarch.fbi.internship.todolg.model.entities.Group;

/**
 * Konwerter jsona grup.
 */
@Component
public class GroupJsonConverter implements IConverter<NewGroup, Group> {

    @Override
    public Group convert(final NewGroup object) {
        Group group = new Group();
        group.setTitle(object.getTitle());

        return group;
    }
}