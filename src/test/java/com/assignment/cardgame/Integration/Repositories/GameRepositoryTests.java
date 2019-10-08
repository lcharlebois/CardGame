package com.assignment.cardgame.Integration.Repositories;

import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.repositories.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTests {

    @Autowired
    GameRepository gameRepository;

    @Test
    public void testCreateReadDelete(){
        Game createdGame = gameRepository.save(new Game());

        Iterable<Game> games = gameRepository.findAll();
        Assert.assertTrue(exists(games, createdGame.getId()));

        gameRepository.deleteById(createdGame.getId());
        Assert.assertFalse(gameRepository.findById(createdGame.getId()).isPresent());
    }

    private boolean exists(Iterable<Game> games, int idToSearch){
        for (Game game : games) {
            if (game.getId() == idToSearch){
                return true;
            }
        }

        return false;
    }
}
