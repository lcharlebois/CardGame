package com.assignment.cardgame.repositories;

import com.assignment.cardgame.models.Deck;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
}
