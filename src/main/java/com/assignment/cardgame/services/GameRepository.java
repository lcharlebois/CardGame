package com.assignment.cardgame.services;

import com.assignment.cardgame.models.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
}
