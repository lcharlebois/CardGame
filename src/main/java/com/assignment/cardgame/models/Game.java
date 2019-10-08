package com.assignment.cardgame.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gameDecks", joinColumns=@JoinColumn(name="game_id"))
    List<Card> Cards = new ArrayList();

    public int getId() {
        return id;
    }

    public List<CardDescriptor> getGameDeck() {
        return this.Cards.stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());
    }

    public void addToGameDeck(List<CardDescriptor> cardDescriptors) {
        List<Card> cardsToAdd = cardDescriptors.stream()
                .map(x -> this.MapCard(x))
                .collect(Collectors.toList());

        Cards.addAll(cardsToAdd);
    }

    public CardDescriptor PickCard(){
        if (this.Cards.isEmpty()){
            return null;
        }

        Card card = this.Cards.remove(0);
        return this.MapCard(card);
    }

    public void shuffleGameDeck(){
        Random randomGenerator = new Random();
        for (int i = this.Cards.size() - 1; i > 0; i--) {
            int index = randomGenerator.nextInt(i + 1);
            this.swapCards(i, index);
        }
    }

    private CardDescriptor MapCard(Card card) {
        return new CardDescriptor(card.face, card.suit);
    }

    private Card MapCard(CardDescriptor card) {
        return new Card(card.face, card.suit);
    }

    private void swapCards(int index1, int index2) {
        Card temp = this.Cards.get(index2);
        this.Cards.set(index2, this.Cards.get(index1));
        this.Cards.set(index1, temp);
    }
}