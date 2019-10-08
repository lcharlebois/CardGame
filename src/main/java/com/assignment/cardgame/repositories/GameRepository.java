package com.assignment.cardgame.repositories;

import com.assignment.cardgame.models.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
}
