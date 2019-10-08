package com.assignment.cardgame.Integration.Repositories;

import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.repositories.DeckRepository;
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
public class DeckRepositoryTests {

    @Autowired
    DeckRepository deckRepository;

    @Test
    public void testCreateReadDelete(){
        Deck createdDeck = deckRepository.save(new Deck());

        Iterable<Deck> decks = deckRepository.findAll();
        Assert.assertTrue(exists(decks, createdDeck.getId()));

        deckRepository.deleteById(createdDeck.getId());
        Assert.assertFalse(deckRepository.findById(createdDeck.getId()).isPresent());
    }

    private boolean exists(Iterable<Deck> decks, int idToSearch){
        for (Deck deck : decks) {
            if (deck.getId() == idToSearch){
                return true;
            }
        }

        return false;
    }
}
