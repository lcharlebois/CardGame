package com.assignment.cardgame.EndToEnd;

import com.assignment.cardgame.services.Dtos.DeckDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DeckControllerTests {

    @Test // Requirement 2
    public void testCreateDeck(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/decks";

        // When
        ResponseEntity<DeckDto> deck = restTemplate.postForEntity(url, null, DeckDto.class);

        // Then
        Assert.assertNotNull(deck);
        Assert.assertEquals(200, deck.getStatusCodeValue());
        Assert.assertNotNull(deck.getBody());
        Assert.assertNotNull(deck.getBody().Cards);
        Assert.assertEquals(52, deck.getBody().Cards.size());
    }

    @Test
    public void testGetDecks(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/decks";
        DeckDto createdDeck1 = restTemplate.postForEntity(url, null, DeckDto.class).getBody();
        DeckDto createdDeck2 = restTemplate.postForEntity(url, null, DeckDto.class).getBody();

        // When
        DeckDto[] decks = restTemplate.getForObject(url, DeckDto[].class);

        // Then
        Assert.assertNotNull(decks);
        Assert.assertTrue(exists(decks, createdDeck1.id));
        Assert.assertTrue(exists(decks, createdDeck2.id));
    }

    @Test
    public void testGetDeck(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/decks";
        DeckDto createdDeck = restTemplate.postForEntity(url, null, DeckDto.class).getBody();

        // When
        DeckDto deck = restTemplate.getForObject(url + "/"  + createdDeck.id, DeckDto.class);

        // Then
        Assert.assertNotNull(deck);
        Assert.assertNotNull(deck.Cards);
    }

    @Test
    public void testDeleteDeck(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/decks";
        DeckDto createdDeck = restTemplate.postForEntity(url, null, DeckDto.class).getBody();

        // When
        restTemplate.delete(url + "/" + createdDeck.id);

        // Then
        DeckDto[] decks = restTemplate.getForObject(url, DeckDto[].class);
        Assert.assertFalse(exists(decks, createdDeck.id));
    }

    private boolean exists(DeckDto[] decks, int idToSearch){
        for (DeckDto deck : decks) {
            if (deck.id == idToSearch){
                return true;
            }
        }

        return false;
    }
}
