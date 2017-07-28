package com.comarch.fbi.internship.todolg.endpoints.converters;

import org.springframework.stereotype.Component;

import com.comarch.fbi.internship.todolg.endpoints.api.dto.GroupEndpoint;
import com.comarch.fbi.internship.todolg.model.entities.Group;


/**
 * Konweter grupy z modelu na grupę endpointów.
 */
@Component
public class GroupConverter implements IConverter<Group, GroupEndpoint> {

    @Override
    public GroupEndpoint convert(final Group model) {
        GroupEndpoint group = new GroupEndpoint();

        if (model == null) {
            return group;
        }

        group.setId(model.getId().intValue());
        group.setTitle(model.getTitle());

        return group;
    }
}