package com.comarch.fbi.internship.todolg.services.api;

import java.util.List;

import javax.validation.Valid;

import com.comarch.fbi.internship.todolg.model.entities.Group;

/**
 * Interfejs serwisu zarządzającego grupami.
 */
public interface IGroupService {

    /**
     * Dodaje testowe grupy.
     *
     * @param count ilość grup do dodania
     */
    void addTestGroups(Integer count);

    /**
     * Pobiera listę wszystkich grup.
     *
     * @return lista wszystkich grup
     */
    List<Group> getAllGroups();

    /**
     * Pobiera grupę przez id.
     *
     * @param id numer grupy
     *
     * @return Grupa o zadanym id
     */
    Group getGroupById(Integer id);

    /**
     * Tworzy nową grupę.
     *
     * @param group nowa grupa
     *
     * @return stworzona grupa
     */
    Group createGroup(@Valid Group group);

    /**
     * Aktualizuję grupę.
     *
     * @param id           numer grupy
     * @param groupUpdated zaaktualizowana grupa
     *
     * @return zaaktualizowana grupa
     */
    Group updateGroup(Long id, @Valid Group groupUpdated);

    /**
     * Usuwa grupę.
     *
     * @param id numer grup
     */
    void deleteGroup(Long id);

    /**
     * Pobiera grupę o tytule zawierającym frazę.
     *
     * @param title fragment tytułu
     *
     * @return lista grup spełniających warunek.
     */
    List<Group> getGroupByTitle(String title);
}