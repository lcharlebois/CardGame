package com.assignment.cardgame.models;

import com.assignment.cardgame.common.Suit;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gameDecks", joinColumns=@JoinColumn(name="game_id"))
    private List<Card> cards = new ArrayList();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="players", joinColumns=@JoinColumn(name="game_id"))
    private Set<Player> playerList = new HashSet();

    public int getId() {
        return id;
    }

    public List<CardDescriptor> getGameDeck() {
        return this.cards.stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());
    }

    public void addToGameDeck(List<CardDescriptor> cardDescriptors) {
        List<Card> cardsToAdd = cardDescriptors.stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());

        cards.addAll(cardsToAdd);
    }

    public CardDescriptor PickCard(){
        if (this.cards.isEmpty()){
            return null;
        }

        Card card = this.cards.remove(0);
        return this.MapCard(card);
    }

    public void shuffleGameDeck(){
        Random randomGenerator = new Random();
        for (int i = this.cards.size() - 1; i > 0; i--) {
            int index = randomGenerator.nextInt(i + 1);
            this.swapCards(i, index);
        }
    }

    public SuitCounts getSuitCounts(){
        Map<Suit, List<Card>> groupedSuits = this.cards.stream().collect(Collectors.groupingBy(c -> c.getSuit()));

        List<Card> hearts = groupedSuits.get(Suit.HEARTS) == null ? new ArrayList<Card>() : groupedSuits.get(Suit.HEARTS);
        List<Card> spades = groupedSuits.get(Suit.SPADES) == null ? new ArrayList<Card>() : groupedSuits.get(Suit.SPADES);
        List<Card> clubs = groupedSuits.get(Suit.CLUBS) == null ? new ArrayList<Card>() : groupedSuits.get(Suit.CLUBS);
        List<Card> diamonds = groupedSuits.get(Suit.DIAMONDS) == null ? new ArrayList<Card>() : groupedSuits.get(Suit.DIAMONDS);

        return new SuitCounts(
                hearts.size(),
                spades.size(),
                clubs.size(),
                diamonds.size());
    }

    public List<CardCount> getSortedCardCount(){
        Map<Card, Long> cardCountMap = this.cards.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<CardCount> cardCounts = new ArrayList();
        for (Map.Entry<Card, Long> entry : cardCountMap.entrySet()) {
            CardDescriptor cardDescriptor = this.MapCard(entry.getKey());
            cardCounts.add(new CardCount(cardDescriptor, entry.getValue().intValue()));
        }

        Comparator<CardCount> comparator = Comparator.comparing(CardCount::getCardSuite)
                .thenComparing(CardCount::getCardFace)
                .reversed();

        Collections.sort(cardCounts, comparator);

        return cardCounts;
    }

    public List<PlayerDescriptor> getPlayerList() {
        return this.playerList.stream()
                .map(x -> this.MapPlayer(x))
                .collect(Collectors.toList());
    }

    public List<PlayerValueDescriptor> getSortedPlayerValues() {
        List<PlayerValueDescriptor> playerValues = this.playerList.stream()
                .map(x -> this.MapPlayerValue(x))
                .collect(Collectors.toList());

        Comparator<PlayerValueDescriptor> comparator = Comparator.comparing(PlayerValueDescriptor::getPlayerValue).reversed();

        Collections.sort(playerValues, comparator);

        return playerValues;
    }

    public void addPlayer(Player player) throws ValidationException {
        if (this.isPlayerAlreadyPlaying(player.getId())){
            throw new ValidationException("Cannot add a player twice to the same game");
        }

        this.playerList.add(player);
    }

    public void removePlayer(int playerId) throws ValidationException {
        Player player = this.findPlayer(playerId);
        this.playerList.remove(player);
    }

    public void dealCardToPlayer(int playerId, int numberOfCard) throws ValidationException {
        Player player = this.findPlayer(playerId);

        List<Card> cards = this.cards.stream().limit(numberOfCard).collect(Collectors.toList());
        player.addCards(cards);
        this.cards.removeAll(cards);
    }

    public PlayerDescriptor getPlayer(int playerId) throws ValidationException {
        return this.MapPlayer(this.findPlayer(playerId));
    }

    private PlayerDescriptor MapPlayer(Player player) {
        return new PlayerDescriptor(player.getId(), player.getPlayerCards());
    }

    private CardDescriptor MapCard(Card card) {
        return new CardDescriptor(card.face, card.suit);
    }

    private Card MapCard(CardDescriptor card) {
        return new Card(card.getFace(), card.getSuit());
    }

    private void swapCards(int index1, int index2) {
        Card temp = this.cards.get(index2);
        this.cards.set(index2, this.cards.get(index1));
        this.cards.set(index1, temp);
    }

    private Player findPlayer(int playerId) throws ValidationException {
        for (Player player : this.playerList) {
            if (player.getId() == playerId){
                return player;
            }
        }

        throw new ValidationException("Unknown player with Id=" + playerId);
    }

    private boolean isPlayerAlreadyPlaying(int playerId) throws ValidationException {
        for (Player player : this.playerList) {
            if (player.getId() == playerId){
                return true;
            }
        }

        return false;
    }

    private PlayerValueDescriptor MapPlayerValue(Player player) {
        return new PlayerValueDescriptor(player.getId(), player.GetCardsValue());
    }
}