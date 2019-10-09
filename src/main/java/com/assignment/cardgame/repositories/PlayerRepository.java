package com.assignment.cardgame.repositories;

import com.assignment.cardgame.models.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
