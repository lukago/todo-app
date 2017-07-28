package com.comarch.fbi.internship.todolg.model.repositories;

import org.springframework.stereotype.Repository;

import com.comarch.fbi.internship.todolg.model.entities.Group;
import com.comarch.fbi.internship.todolg.model.repositories.common.BaseJpaRepository;

/**
 * Repozytorium zarządzające zadaniami.
 */
@Repository
public class GroupsRepository extends BaseJpaRepository<Group, Long> {
}