package com.comarch.fbi.internship.todolg.model.repositories;

import org.springframework.stereotype.Repository;

import com.comarch.fbi.internship.todolg.model.entities.Task;
import com.comarch.fbi.internship.todolg.model.repositories.common.BaseJpaRepository;

/**
 * Repozytorium zarządzające grupami.
 */
@Repository
public class TasksRepository extends BaseJpaRepository<Task, Long> {
}