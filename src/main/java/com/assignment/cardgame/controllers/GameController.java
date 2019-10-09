package com.assignment.cardgame.controllers;

import com.assignment.cardgame.controllers.Parameters.DealCardsParameter;
import com.assignment.cardgame.controllers.Parameters.NewDeckParameter;
import com.assignment.cardgame.controllers.Parameters.NewPlayerParameter;
import com.assignment.cardgame.services.Dtos.*;
import com.assignment.cardgame.services.GameManagementService;
import com.assignment.cardgame.services.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameManagementService gameManagementService;

    @PostMapping("/games")
    public GameDto create(){
        return this.gameManagementService.createGame();
    }

    @GetMapping("/games")
    public List<GameDto> getAll(){
        return this.gameManagementService.getAllGames();
    }

    @DeleteMapping("/games/{id}")
    public void delete(@PathVariable Integer id) throws EntityNotFoundException {
        this.gameManagementService.deleteGame(id);
    }

    @PostMapping("/games/{id}/decks")
    public GameDto addDeck(@PathVariable Integer id, @RequestBody NewDeckParameter deckParameter) throws EntityNotFoundException {
        return this.gameManagementService.addDeckToGame(id, deckParameter.getId());
    }

    @PostMapping("/games/{id}/decks/shuffle")
    public GameDto addDeck(@PathVariable Integer id) throws EntityNotFoundException {
        return this.gameManagementService.shuffleGameDeck(id);
    }

    @GetMapping("/games/{id}/decks/cardsPerSuit")
    public CardsPerSuitDto getCardsPerStuits(@PathVariable Integer id) throws EntityNotFoundException {
        return this.gameManagementService.getCardsPerSuits(id);
    }

    @GetMapping("/games/{id}/decks/sortedCardCount")
    public List<CardCountDto> getSortedCardCount(@PathVariable Integer id) throws EntityNotFoundException {
        return this.gameManagementService.getSortedCardCount(id);
    }

    @PostMapping("/games/{id}/players")
    public GameDto addPlayer(@PathVariable Integer id, @RequestBody NewPlayerParameter playerParameter) throws EntityNotFoundException, ValidationException {
        return this.gameManagementService.addPlayerToGame(id, playerParameter.getId());
    }

    @DeleteMapping("/games/{gameId}/players/{playerId}")
    public GameDto removePlayer(@PathVariable Integer gameId, @PathVariable Integer playerId) throws EntityNotFoundException, ValidationException {
        return this.gameManagementService.removePlayerFromGame(gameId, playerId);
    }

    @PostMapping("/games/{gameId}/players/{playerId}/dealCards")
    public GameDto dealCards(@PathVariable Integer gameId, @PathVariable Integer playerId, @RequestBody DealCardsParameter dealCardsParameter) throws EntityNotFoundException, ValidationException {
        return this.gameManagementService.dealCardToPlayer(gameId, playerId, dealCardsParameter.getNumberOfCards());
    }

    @GetMapping("/games/{gameId}/players/{playerId}/cards")
    public List<CardDto> getPlayerCards(@PathVariable Integer gameId, @PathVariable Integer playerId) throws EntityNotFoundException, ValidationException {
        return this.gameManagementService.getPlayerCards(gameId, playerId);
    }

    @GetMapping("/games/{gameId}/players/scores")
    public List<PlayerValueDto> getPlayerValues(@PathVariable Integer gameId) throws EntityNotFoundException {
        return this.gameManagementService.getPlayerValues(gameId);
    }
}
