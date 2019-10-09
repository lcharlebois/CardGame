package com.assignment.cardgame.services;

import com.assignment.cardgame.models.*;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.repositories.GameRepository;
import com.assignment.cardgame.services.Dtos.CardDto;
import com.assignment.cardgame.services.Dtos.DeckDto;
import com.assignment.cardgame.services.Dtos.GameDto;
import com.assignment.cardgame.services.Dtos.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GameManagementService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    DeckRepository deckRepository;

    public GameDto createGame(){
        Game game = gameRepository.save(new Game());
        return this.MapGame(game);
    }

    public List<GameDto> getAllGames(){
        Iterable<Game> games = gameRepository.findAll();
        return StreamSupport.stream(games.spliterator(), false)
                .map(x -> this.MapGame(x))
                .collect(Collectors.toList());
    }

    public void deleteGame(int gameId) throws EntityNotFoundException {
        if (gameRepository.existsById(gameId)){
            gameRepository.deleteById(gameId);
            return;
        }

        throw new EntityNotFoundException("Game with id=" + gameId + " was not found.");
    }

    public GameDto AddDeckToGame(int gameId, int deckId) throws EntityNotFoundException {
        Game game = getGame(gameId);
        Deck deck = getDeck(deckId);

        List<CardDescriptor> cards = deck.GetCards();
        game.addToGameDeck(cards);

        this.gameRepository.save(game);
        this.deckRepository.delete(deck);

        return this.MapGame(game);
    }

    public GameDto AddPlayerToGame(int gameId, int playerId) throws EntityNotFoundException, ValidationException {
        Game game = getGame(gameId);

        game.addPlayer(playerId);

        this.gameRepository.save(game);

        return this.MapGame(game);
    }
//
//    public GameDto DealCardToPlayer(int gameId, int playerId) throws EntityNotFoundException, ValidationException {
//        Game game = getGame(gameId);
//
//        game.addPlayer(playerId);
//
//        this.gameRepository.save(game);
//
//        return this.MapGame(game);
//    }

    private Game getGame(int gameId) throws EntityNotFoundException {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()){
            return game.get();
        }

        throw new EntityNotFoundException("Game with id=" + gameId + " was not found.");
    }

    private Deck getDeck(int deckId) throws EntityNotFoundException {
        Optional<Deck> deck = deckRepository.findById(deckId);

        if (deck.isPresent()){
            return deck.get();
        }

        throw new EntityNotFoundException("Deck with id=" + deckId + " was not found.");
    }

    private GameDto MapGame(Game game) {
        List cards = game.getGameDeck().stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());

        List players = game.getPlayerList().stream()
                .map(x -> this.MapPlayer(x))
                .collect(Collectors.toList());

        return new GameDto(game.getId(), cards, players);
    }

    private CardDto MapCard(CardDescriptor cardDescriptor) {
        return new CardDto(cardDescriptor.getFace(), cardDescriptor.getSuit());
    }

    private PlayerDto MapPlayer(PlayerDescriptor playerDescriptor) {
        List cards = playerDescriptor.getCards().stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());

        return new PlayerDto(playerDescriptor.getId(), cards);
    }
}
