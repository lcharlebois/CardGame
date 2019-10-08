package com.assignment.cardgame.services;

import com.assignment.cardgame.models.CardDescriptor;
import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return this.MapGame(game);
    }
//
//    public GameDto AddPlayerToGame(int gameId, int deckId) throws EntityNotFoundException {
//        Game game = getGame(gameId);
//        Deck deck = getDeck(deckId);
//
//        List<CardDescriptor> cards = deck.GetCards();
//        game.addToGameDeck(cards);
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

        return new GameDto(game.getId(), cards);
    }

    private CardDto MapCard(CardDescriptor cardDescriptor) {
        return new CardDto(cardDescriptor.getFace(), cardDescriptor.getSuit());
    }

    private DeckDto MapDeck(Deck deck) {
        List<String> cards = deck.GetCards().stream().map(x -> x.toString()).collect(Collectors.toList());
        return new DeckDto(deck.getId(), cards);
    }
}
