package com.assignment.cardgame.EndToEnd;

import com.assignment.cardgame.controllers.DealCardsParameter;
import com.assignment.cardgame.controllers.NewDeckParameter;
import com.assignment.cardgame.controllers.NewPlayerParameter;
import com.assignment.cardgame.services.Dtos.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

public class GameControllerTests {

    @Test // Requirement 1
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

    @Test // Requirement 1 (2)
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

    @Test  // Requirement 3
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
        GameDto game = restTemplate.postForEntity(addDeckUrl, new NewDeckParameter(deckDto.id), GameDto.class).getBody();

        // Then
        Assert.assertNotNull(game.id);
        Assert.assertEquals(52, game.cards.size());
    }

    @Test  // Requirement 4
    public void testAddPlayerToGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String gamesUrl =  "http://localhost:8080/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // When
        int playerId = new Random().nextInt(99999999);
        String addPlayerUrl = gamesUrl + "/" + createdGame.id + "/players";
        GameDto game = restTemplate.postForEntity(addPlayerUrl, new NewPlayerParameter(playerId), GameDto.class).getBody();

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(1, game.players.size());
        Assert.assertEquals(playerId, game.players.get(0).getId());
    }

    @Test  // Requirement 4 (2)
    public void testRemovePlayerFromGame(){
        //
        RestTemplate restTemplate = new RestTemplate();
        String gamesUrl =  "http://localhost:8080/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // addPlayer
        int playerId = new Random().nextInt(99999999);
        String addPlayerUrl = gamesUrl + "/" + createdGame.id + "/players";
        GameDto game = restTemplate.postForEntity(addPlayerUrl, new NewPlayerParameter(playerId), GameDto.class).getBody();
        Assert.assertEquals(1, game.players.size());

        // Delete player
        String removePlayerUrl = gamesUrl + "/" + createdGame.id + "/players/" + playerId;
        restTemplate.delete(removePlayerUrl, GameDto.class);

        // then
        GameDto emptyGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();
        Assert.assertEquals(0, emptyGame.players.size());
    }

    @Test  // Requirement 5
    public void testDealCardsToPlayer(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";

        // Create Game
        String gamesUrl =  url + "/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        String addPlayerUrl = gamesUrl + "/" + createdGame.id + "/players";
        restTemplate.postForEntity(addPlayerUrl, new NewPlayerParameter(playerId), GameDto.class).getBody();

        //Create deck
        String decksUrl =  url + "/decks";
        DeckDto createdDeck = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add cards
        String addDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(addDeckUrl, new NewDeckParameter(createdDeck.id), GameDto.class).getBody();

        // Deal Cards
        String dealCardUrl = addPlayerUrl + "/" + playerId + "/dealCards";
        GameDto game = restTemplate.postForEntity(dealCardUrl, new DealCardsParameter(52), GameDto.class).getBody();

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(1, game.players.size());
        Assert.assertEquals(playerId, game.players.get(0).getId());
        Assert.assertEquals(52, game.players.get(0).getCards().size());
        Assert.assertEquals(0, game.cards.size());
    }

    @Test // Requirement 5 (2)
    public void testDeal53Cards(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";

        // Create Game
        String gamesUrl =  url + "/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        String addPlayerUrl = gamesUrl + "/" + createdGame.id + "/players";
        restTemplate.postForEntity(addPlayerUrl, new NewPlayerParameter(playerId), GameDto.class).getBody();

        //Create deck
        String decksUrl =  url + "/decks";
        DeckDto createdDeck = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add cards
        String addDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(addDeckUrl, new NewDeckParameter(createdDeck.id), GameDto.class).getBody();

        // Deal Cards
        GameDto game = null;
        for (int i = 0 ; i < 53 ; i ++){
            String dealCardUrl = addPlayerUrl + "/" + playerId + "/dealCards";
            game = restTemplate.postForEntity(dealCardUrl, new DealCardsParameter(1), GameDto.class).getBody();
        }

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(1, game.players.size());
        Assert.assertEquals(playerId, game.players.get(0).getId());
        Assert.assertEquals(52, game.players.get(0).getCards().size());
        Assert.assertEquals(0, game.cards.size());
    }

    @Test  // Requirement 6
    public void testGetPlayerCards(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";

        // Create Game
        String gamesUrl =  url + "/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        String playerUrl = gamesUrl + "/" + createdGame.id + "/players";
        restTemplate.postForEntity(playerUrl, new NewPlayerParameter(playerId), GameDto.class).getBody();

        //Create deck
        String decksUrl =  url + "/decks";
        DeckDto createdDeck = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add cards
        String addDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(addDeckUrl, new NewDeckParameter(createdDeck.id), GameDto.class).getBody();

        // Deal Cards
        String dealCardUrl = playerUrl + "/" + playerId + "/dealCards";
        GameDto game = restTemplate.postForEntity(dealCardUrl, new DealCardsParameter(5), GameDto.class).getBody();

        // Get cards
        String getCardUrl = playerUrl + "/" + playerId + "/cards";
        CardDto[] cards = restTemplate.getForEntity(getCardUrl, CardDto[].class).getBody();

        // Then
        Assert.assertEquals(5, cards.length);
    }

    @Test  // Requirement 7
    public void testGetPlayerScore() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";
        Random random = new Random();

        // Create Game
        String gamesUrl =  url + "/games";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create player 1
        int playerId1 = random.nextInt(99999999);
        String playersUrl = gamesUrl + "/" + createdGame.id + "/players";
        restTemplate.postForEntity(playersUrl, new NewPlayerParameter(playerId1), GameDto.class).getBody();

        // Create player 2
        int playerId2 = new Random().nextInt(99999999);
        restTemplate.postForEntity(playersUrl, new NewPlayerParameter(playerId2), GameDto.class).getBody();

        //Create deck
        String decksUrl =  url + "/decks";
        DeckDto createdDeck = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add cards
        String addDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(addDeckUrl, new NewDeckParameter(createdDeck.id), GameDto.class).getBody();

        // Deal 15 Cards to player 1
        String dealCardUrl1 = playersUrl + "/" + playerId1 + "/dealCards";
        restTemplate.postForEntity(dealCardUrl1, new DealCardsParameter(2), GameDto.class).getBody();

        // Deal 15 Cards to player 1
        String dealCardUrl2 = playersUrl + "/" + playerId2 + "/dealCards";
        GameDto game = restTemplate.postForEntity(dealCardUrl2, new DealCardsParameter(2), GameDto.class).getBody();

        // Get player values
        String getCardUrl = playersUrl + "/scores";
        PlayerValueDto[] scores = restTemplate.getForEntity(getCardUrl, PlayerValueDto[].class).getBody();

        // Then the two player are sorted
        Assert.assertEquals(2, scores.length);
        Assert.assertEquals( this.getValueForPlayer(game, playerId1), findPlayer(scores, playerId1).getPlayerValue());
        Assert.assertEquals( this.getValueForPlayer(game, playerId2), findPlayer(scores, playerId2).getPlayerValue());
        Assert.assertTrue(scores[0].getPlayerValue() > scores[1].getPlayerValue());
    }

    @Test // Requirement 8
    public void testGetCountOfCardPerSuit(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";
        String gamesUrl =  url + "/games";
        String decksUrl =  url + "/decks";

        // Create game
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create deck
        DeckDto deckDto = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add deck to game
        String gameDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(gameDeckUrl, new NewDeckParameter(deckDto.id), GameDto.class).getBody();

        // Get cards per suits
        String cardPerSuitUrl = gameDeckUrl + "/cardsPerSuit";
        CardsPerSuitDto cardsPerSuit = restTemplate.getForEntity(cardPerSuitUrl, CardsPerSuitDto.class).getBody();

        // Then
        Assert.assertNotNull(cardsPerSuit);
        Assert.assertEquals(13, cardsPerSuit.getClubsCount());
        Assert.assertEquals(13, cardsPerSuit.getDiamondsCount());
        Assert.assertEquals(13, cardsPerSuit.getHeartsCount());
        Assert.assertEquals(13, cardsPerSuit.getSpadesCount());
    }

    @Test // Requirement 9
    public void testGetSortedCardCount(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";
        String gamesUrl =  url + "/games";
        String decksUrl =  url + "/decks";

        // Create game
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();

        // Create deck 1
        DeckDto deckDto1 = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Create deck 2
        DeckDto deckDto2 = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();

        // Add deck to game
        String gameDeckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        restTemplate.postForEntity(gameDeckUrl, new NewDeckParameter(deckDto1.id), GameDto.class).getBody();
        restTemplate.postForEntity(gameDeckUrl, new NewDeckParameter(deckDto2.id), GameDto.class).getBody();

        // Get cards per suits
        String cardPerSuitUrl = gameDeckUrl + "/sortedCardCount";
        CardCountDto[] cardCounts = restTemplate.getForEntity(cardPerSuitUrl, CardCountDto[].class).getBody();

        // Then
        Assert.assertNotNull(cardCounts);
        Assert.assertEquals(2, cardCounts[0].getCount());
    }

    @Test // Requirement 10
    public void testShuffle(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080";
        String gamesUrl =  url + "/games";
        String decksUrl =  url + "/decks";
        GameDto createdGame = restTemplate.postForEntity(gamesUrl, null, GameDto.class).getBody();
        DeckDto deckDto = restTemplate.postForEntity(decksUrl, null, DeckDto.class).getBody();
        String deckUrl = gamesUrl + "/" + createdGame.id + "/decks";
        List<CardDto> cards = restTemplate.postForEntity(deckUrl, new NewDeckParameter(deckDto.id), GameDto.class).getBody().cards;

        // When
        String shuffleDeckUrl = deckUrl + "/shuffle";
        List<CardDto> shuffledGameDeck = restTemplate.postForEntity(shuffleDeckUrl, null, GameDto.class).getBody().cards;
        List<CardDto> veryShuffledGameDeck = restTemplate.postForEntity(shuffleDeckUrl, null, GameDto.class).getBody().cards;

        // Then
        Assert.assertEquals(52, shuffledGameDeck.size());

        Assert.assertNotEquals(cards.get(0), shuffledGameDeck.get(0));
        Assert.assertNotEquals(veryShuffledGameDeck.get(0), shuffledGameDeck.get(0));

        Assert.assertNotEquals(cards.get(30), shuffledGameDeck.get(30));
        Assert.assertNotEquals(veryShuffledGameDeck.get(30), shuffledGameDeck.get(30));

        Assert.assertNotEquals(cards.get(51), shuffledGameDeck.get(51));
        Assert.assertNotEquals(veryShuffledGameDeck.get(51), shuffledGameDeck.get(51));
    }

    private boolean exists(GameDto[] games, int idToSearch){
        for (GameDto game : games) {
            if (game.id == idToSearch){
                return true;
            }
        }

        return false;
    }

    private int getValueForPlayer(GameDto gameDto, int playerId) throws Exception {
        PlayerDto playerDto = this.findPlayer(gameDto, playerId);

        int value = 0;
        for (CardDto card : playerDto.getCards()) {
            value += (card.face.getValue() + 1);
        }

        return value;
    }

    private PlayerDto findPlayer(GameDto gameDto, int playerId) throws Exception {
        for (PlayerDto playerDto : gameDto.players) {
            if (playerId == playerDto.getId()) return playerDto;
        }

        throw new Exception("Player not found");
    }

    private PlayerValueDto findPlayer(PlayerValueDto[] playerValues, int playerId) throws Exception {
        for (PlayerValueDto playerValue : playerValues) {
            if (playerId == playerValue.getPlayerId()) return playerValue;
        }

        throw new Exception("Player not found");
    }
}
