package com.assignment.cardgame.EndToEnd;

import com.assignment.cardgame.controllers.DeckParameter;
import com.assignment.cardgame.services.DeckDto;
import com.assignment.cardgame.services.GameDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GameControllerTests {

    @Test
    public void testCreateGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/games";

        // When
        ResponseEntity<GameDto> game = restTemplate.postForEntity(url, null, GameDto.class);

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(200, game.getStatusCodeValue());
        Assert.assertNotNull(game.getBody());
    }

    @Test
    public void testGetGames(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/games";
        GameDto createdGame1 = restTemplate.postForEntity(url, null, GameDto.class).getBody();
        GameDto createdGame2 = restTemplate.postForEntity(url, null, GameDto.class).getBody();

        // When
        GameDto[] games = restTemplate.getForObject(url, GameDto[].class);

        // Then
        Assert.assertNotNull(games);
        Assert.assertTrue(exists(games, createdGame1.id));
        Assert.assertTrue(exists(games, createdGame2.id));
    }

    @Test
    public void testAddGameDeck(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";
        String gamesUrl =  url + "/games";
        String decksUrl =  url + "/decks";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();
        DeckDto deckDto = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // When
        String addDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        GameDto game = restTemplate.postForEntity(addDeckUrl, new DeckParameter(deckDto.id), GameDto.class).getBody();

        // Then
        Assert.assertNotNull(game.id);
        Assert.assertEquals(52, game.Cards.size());
    }

//    @Test todo fix it
//    public void testDealCardDeck(){
//        // Given
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/deck";
//        DeckDto createdDeck = restTemplate.postForEntity(url, null, DeckDto.class).getBody();
//
//        // When
//        String card = restTemplate.postForEntity(url + "/"  + createdDeck.id, null, String.class).getBody();
//
//        // Then
//        DeckDto deck = restTemplate.getForObject(url + "/"  + createdDeck.id, DeckDto.class);
//        Assert.assertNotNull(card);
//    }

    @Test
    public void testDeleteGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/games";
        GameDto createdGame = restTemplate.postForEntity(url, null, GameDto.class).getBody();

        // When
        restTemplate.delete(url + "/" + createdGame.id);

        // Then
        GameDto[] games = restTemplate.getForObject(url, GameDto[].class);
        Assert.assertFalse(exists(games, createdGame.id));
    }

    private boolean exists(GameDto[] games, int idToSearch){
        for (GameDto game : games) {
            if (game.id == idToSearch){
                return true;
            }
        }

        return false;
    }
}
