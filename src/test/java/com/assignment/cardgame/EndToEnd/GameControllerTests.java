package com.assignment.cardgame.EndToEnd;

import com.assignment.cardgame.controllers.Parameters.DealCardsParameter;
import com.assignment.cardgame.controllers.Parameters.NewDeckParameter;
import com.assignment.cardgame.controllers.Parameters.NewPlayerParameter;
import com.assignment.cardgame.services.Dtos.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

public class GameControllerTests {

    static String baseUrl = "http://localhost:8080";

    @Test // Requirement 1
    public void testCreateGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();

        // When
        ResponseEntity<GameDto> game = CreateGame(restTemplate);

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(200, game.getStatusCodeValue());
        Assert.assertNotNull(game.getBody());
    }

    private ResponseEntity<GameDto> CreateGame(RestTemplate restTemplate) {
        return restTemplate.postForEntity(baseUrl + "/games", null, GameDto.class);
    }

    @Test // Requirement 1 (2)
    public void testDeleteGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // When
        restTemplate.delete(baseUrl + "/games/" + createdGame.id);

        // Then
        GameDto[] games = restTemplate.getForObject(baseUrl + "/games", GameDto[].class);
        Assert.assertFalse(exists(games, createdGame.id));
    }

    @Test  // Requirement 3
    public void testAddGameDeck(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        GameDto createdGame = CreateGame(restTemplate).getBody();
        DeckDto deckDto = createDeck(restTemplate).getBody();

        // When
        GameDto game = addDeckToGame(restTemplate, createdGame, deckDto).getBody();

        // Then
        Assert.assertNotNull(game.id);
        Assert.assertEquals(52, game.cards.size());
    }

    @Test  // Requirement 4
    public void testAddPlayerToGame(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // When
        int playerId = new Random().nextInt(99999999);
        GameDto game = createPlayer(restTemplate, createdGame, playerId).getBody();

        // Then
        Assert.assertNotNull(game);
        Assert.assertEquals(1, game.players.size());
        Assert.assertEquals(playerId, game.players.get(0).getId());
    }

    @Test  // Requirement 4 (2)
    public void testRemovePlayerFromGame(){
        RestTemplate restTemplate = new RestTemplate();

        // Create game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // addPlayer
        int playerId = new Random().nextInt(99999999);
        GameDto game = createPlayer(restTemplate, createdGame, playerId).getBody();
        Assert.assertEquals(1, game.players.size());

        // Delete player
        restTemplate.delete(baseUrl + "/games/" + createdGame.id + "/players/" + playerId, GameDto.class);

        // then
        GameDto emptyGame = CreateGame(restTemplate).getBody();
        Assert.assertEquals(0, emptyGame.players.size());
    }

    @Test  // Requirement 5
    public void testDealCardsToPlayer(){
        RestTemplate restTemplate = new RestTemplate();

        // Create Game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        createPlayer(restTemplate, createdGame, playerId).getBody();

        //Create deck
        DeckDto createdDeck = createDeck(restTemplate).getBody();

        // Add cards
        addDeckToGame(restTemplate, createdGame, createdDeck).getBody();

        // Deal Cards
        GameDto game = dealCard(restTemplate, createdGame, playerId, 52).getBody();

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

        // Create Game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        createPlayer(restTemplate, createdGame, playerId).getBody();

        //Create deck
        DeckDto createdDeck = createDeck(restTemplate).getBody();

        // Add cards
        addDeckToGame(restTemplate, createdGame, createdDeck).getBody();

        // Deal Cards
        GameDto game = null;
        for (int i = 0 ; i < 53 ; i ++){
            game = dealCard(restTemplate, createdGame, playerId, 1).getBody();
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

        // Create Game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create player
        int playerId = new Random().nextInt(99999999);
        createPlayer(restTemplate, createdGame, playerId).getBody();

        //Create deck
        DeckDto createdDeck = createDeck(restTemplate).getBody();

        // Add cards
        addDeckToGame(restTemplate, createdGame, createdDeck).getBody();

        // Deal Cards
        GameDto game = dealCard(restTemplate, createdGame, playerId, 5).getBody();

        // Get cards
        String getCardUrl = (baseUrl + "/games/" + createdGame.id + "/players") + "/" + playerId + "/cards";
        CardDto[] cards = restTemplate.getForEntity(getCardUrl, CardDto[].class).getBody();

        // Then
        Assert.assertEquals(5, cards.length);
    }

    @Test  // Requirement 7
    public void testGetPlayerScore() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Random random = new Random();

        // Create Game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create player 1
        int playerId1 = random.nextInt(99999999);
        createPlayer(restTemplate, createdGame, playerId1).getBody();

        // Create player 2
        int playerId2 = new Random().nextInt(99999999);
        createPlayer(restTemplate, createdGame, playerId2);

        //Create deck
        DeckDto createdDeck = createDeck(restTemplate).getBody();

        // Add cards
        addDeckToGame(restTemplate, createdGame, createdDeck).getBody();

        // Deal 15 Cards to player 1
        dealCard(restTemplate, createdGame, playerId1, 15).getBody();

        // Deal 15 Cards to player 1
        GameDto game = dealCard(restTemplate, createdGame, playerId2, 15).getBody();

        // Get player values
        PlayerValueDto[] scores = restTemplate.getForEntity(baseUrl + "/games/" + createdGame.id + "/players/scores", PlayerValueDto[].class).getBody();

        // Then the two player are sorted
        Assert.assertEquals(2, scores.length);
        Assert.assertEquals( this.getValueForPlayer(game, playerId1), findPlayer(scores, playerId1).getPlayerValue());
        Assert.assertEquals( this.getValueForPlayer(game, playerId2), findPlayer(scores, playerId2).getPlayerValue());
        Assert.assertTrue(scores[0].getPlayerValue() > scores[1].getPlayerValue());
    }

    @Test // Requirement 8
    public void testGetCountOfCardPerSuit(){
        RestTemplate restTemplate = new RestTemplate();

        // Create game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create deck
        DeckDto deckDto = createDeck(restTemplate).getBody();

        // Add deck to game
        addDeckToGame(restTemplate, createdGame, deckDto).getBody();

        // Get cards per suits
        String cardPerSuitUrl = baseUrl + "/games/" + createdGame.id + "/decks/cardsPerSuit";
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
        RestTemplate restTemplate = new RestTemplate();

        // Create game
        GameDto createdGame = CreateGame(restTemplate).getBody();

        // Create deck 1
        DeckDto deckDto1 = createDeck(restTemplate).getBody();

        // Create deck 2
        DeckDto deckDto2 = createDeck(restTemplate).getBody();

        // Add deck to game
        addDeckToGame(restTemplate, createdGame, deckDto1).getBody();
        addDeckToGame(restTemplate, createdGame, deckDto2).getBody();

        // Get sorted card count
        String cardPerSuitUrl = baseUrl + "/games/" + createdGame.id + "/decks/sortedCardCount";
        CardCountDto[] cardCounts = restTemplate.getForEntity(cardPerSuitUrl, CardCountDto[].class).getBody();

        // Then
        Assert.assertNotNull(cardCounts);
        Assert.assertEquals(2, cardCounts[0].count);
    }

    @Test // Requirement 10
    public void testShuffle(){
        RestTemplate restTemplate = new RestTemplate();

        // Given
        GameDto createdGame = CreateGame(restTemplate).getBody();
        DeckDto deckDto = createDeck(restTemplate).getBody();
        List<CardDto> cards = addDeckToGame(restTemplate, createdGame, deckDto).getBody().cards;

        // When
        String shuffleDeckUrl = baseUrl + "/games/" + createdGame.id + "/decks/shuffle";
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

    @Test
    public void testGetGames(){
        // Given
        RestTemplate restTemplate = new RestTemplate();
        GameDto createdGame1 = CreateGame(restTemplate).getBody();
        GameDto createdGame2 = CreateGame(restTemplate).getBody();

        // When
        GameDto[] games = restTemplate.getForObject(baseUrl + "/games", GameDto[].class);

        // Then
        Assert.assertNotNull(games);
        Assert.assertTrue(exists(games, createdGame1.id));
        Assert.assertTrue(exists(games, createdGame2.id));
    }

    private ResponseEntity<GameDto> dealCard(RestTemplate restTemplate, GameDto createdGame, int playerId, int numberOfCard) {
        return restTemplate.postForEntity(baseUrl + "/games/" + createdGame.id + "/players/" + playerId + "/dealCards", new DealCardsParameter(numberOfCard), GameDto.class);
    }

    private ResponseEntity<GameDto> addDeckToGame(RestTemplate restTemplate, GameDto createdGame, DeckDto deckDto1) {
        return restTemplate.postForEntity(baseUrl + "/games/" + createdGame.id + "/decks", new NewDeckParameter(deckDto1.id), GameDto.class);
    }

    private ResponseEntity<DeckDto> createDeck(RestTemplate restTemplate) {
        return restTemplate.postForEntity(baseUrl + "/decks", null, DeckDto.class);
    }

    private ResponseEntity<GameDto> createPlayer(RestTemplate restTemplate, GameDto createdGame, int playerId) {
        return restTemplate.postForEntity(baseUrl + "/games/" + createdGame.id + "/players", new NewPlayerParameter(playerId), GameDto.class);
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
