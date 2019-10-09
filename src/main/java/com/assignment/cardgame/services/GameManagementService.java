package com.assignment.cardgame.services;

import com.assignment.cardgame.models.*;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.repositories.GameRepository;
import com.assignment.cardgame.repositories.PlayerRepository;
import com.assignment.cardgame.services.Dtos.*;
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

    @Autowired
    PlayerRepository playerRepository;

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

    public GameDto addDeckToGame(int gameId, int deckId) throws EntityNotFoundException {
        Game game = getGame(gameId);
        Deck deck = getDeck(deckId);

        List<CardDescriptor> cards = deck.getCards();
        game.addToGameDeck(cards);

        this.gameRepository.save(game);
        this.deckRepository.delete(deck);

        return this.MapGame(game);
    }

    public GameDto shuffleGameDeck(int gameId) throws EntityNotFoundException {
        Game game = getGame(gameId);
        game.shuffleGameDeck();
        this.gameRepository.save(game);

        return this.MapGame(game);
    }

    public GameDto addPlayerToGame(int gameId, int playerId) throws EntityNotFoundException {
        Game game = getGame(gameId);

        Player player = this.playerRepository.save(new Player(playerId)); // Todo extract the player creation in a controller

        game.addPlayer(player);

        this.gameRepository.save(game);

        return this.MapGame(game);
    }

    public GameDto removePlayerFromGame(int gameId, int playerId) throws EntityNotFoundException, ValidationException {
        Game game = getGame(gameId);
        game.removePlayer(playerId);

        this.gameRepository.save(game);

        return this.MapGame(game);
    }

    public GameDto dealCardToPlayer(int gameId, int playerId, int numberOfCards) throws EntityNotFoundException, ValidationException {
        Game game = getGame(gameId);
        game.dealCardToPlayer(playerId, numberOfCards);
        this.gameRepository.save(game);

        return this.MapGame(game);
    }

    public List<CardDto> getPlayerCards(int gameId, int playerId) throws EntityNotFoundException, ValidationException {
        Game game = getGame(gameId);
        PlayerDescriptor player = game.getPlayer(playerId);

        return this.MapPlayer(player).getCards();
    }

    public List<PlayerValueDto> getPlayerValues(int gameId) throws EntityNotFoundException {
        Game game = getGame(gameId);

        List<PlayerValueDto> playerValues = game.getSortedPlayerValues().stream()
                .map(x -> this.MapPlayerValue(x))
                .collect(Collectors.toList());

        return playerValues;
    }

    public CardsPerSuitDto getCardsPerSuits(int gameId) throws EntityNotFoundException {
        Game game = getGame(gameId);
        SuitCounts suitCounts = game.getSuitCounts();
        return this.MapSuitCounts(suitCounts);
    }

    public List<CardCountDto> getSortedCardCount(int gameId) throws EntityNotFoundException {
        Game game = getGame(gameId);
        List<CardCountDto> cardCounts = game.getSortedCardCount().stream()
                .map(x -> this.MapCardCount(x))
                .collect(Collectors.toList());

        return cardCounts;
    }

    private Game getGame(int gameId) throws EntityNotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isPresent()){
            Game game = gameOptional.get();
            return game;
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

    private PlayerValueDto MapPlayerValue(PlayerValueDescriptor playerValueDescriptor) {
        return new PlayerValueDto(playerValueDescriptor.getPlayerId(), playerValueDescriptor.getPlayerValue());
    }

    private CardsPerSuitDto MapSuitCounts(SuitCounts suitCounts) {
        return new CardsPerSuitDto(
                suitCounts.getHeartsCount(),
                suitCounts.getSpadesCount(),
                suitCounts.getClubsCount(),
                suitCounts.getDiamondsCount());
    }

    private CardCountDto MapCardCount(CardCount cardCount) {
        return new CardCountDto(this.MapCard(cardCount.getCard()), cardCount.getCount());
    }
}
