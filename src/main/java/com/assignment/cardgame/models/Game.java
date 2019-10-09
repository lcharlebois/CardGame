package com.assignment.cardgame.models;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ElementCollection()
    @CollectionTable(name="gameDecks", joinColumns=@JoinColumn(name="game_id"))
    List<Card> cards = new ArrayList();

    @OneToMany(targetEntity = Player.class, mappedBy = "game", fetch = FetchType.EAGER)
    List<Player> playerList = new ArrayList();

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

    public List<PlayerDescriptor> getPlayerList() {
        return this.playerList.stream()
                .map(x -> this.MapPlayer(x))
                .collect(Collectors.toList());
    }

    public void addPlayer(int playerId) throws ValidationException {
        if (this.playerList.stream()
                .filter(o -> o.getId() == playerId)
                .findFirst()
                .isPresent()){

            throw new ValidationException("Cannot add a player with id="
                    + playerId
                    + " is already in the game with id="
                    + this.id);
        }

        this.playerList.add(new Player(playerId));
    }

    private PlayerDescriptor MapPlayer(Player player) {
        return new PlayerDescriptor(player.getId(), player.getPlayerCards());
    }

    private CardDescriptor MapCard(Card card) {
        return new CardDescriptor(card.face, card.suit);
    }

    private Card MapCard(CardDescriptor card) {
        return new Card(card.face, card.suit);
    }

    private void swapCards(int index1, int index2) {
        Card temp = this.cards.get(index2);
        this.cards.set(index2, this.cards.get(index1));
        this.cards.set(index1, temp);
    }
}