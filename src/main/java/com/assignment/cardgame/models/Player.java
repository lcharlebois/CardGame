package com.assignment.cardgame.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Player {

    @Id
    private int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="playerCards", joinColumns=@JoinColumn(name="player_id"))
    private List<Card> cards = new ArrayList();

    public Player(int id) {
        this.id = id;
    }

    protected Player() {
    }

    public int getId() {
        return id;
    }

    public List<CardDescriptor> getPlayerCards() {
        return this.cards.stream()
                .map(x -> this.MapCards(x))
                .collect(Collectors.toList());
    }

    public void addCard(CardDescriptor cardDescriptor) {
        Card card = this.MapCards(cardDescriptor);
        cards.add(card);
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public int GetCardsValue(){
        int cardsValue = 0;
        for (Card card : this.cards) {
            cardsValue += (card.face.getValue() + 1);
        }

        return cardsValue;
    }

    private CardDescriptor MapCards(Card card) {
        return new CardDescriptor(card.face, card.suit);
    }

    private Card MapCards(CardDescriptor card) {
        return new Card(card.face, card.suit);
    }
}