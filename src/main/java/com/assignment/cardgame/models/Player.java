package com.assignment.cardgame.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="playerCard", joinColumns=@JoinColumn(name="player_id"))
    List<Card> Cards = new ArrayList();

    public int getId() {
        return id;
    }

    public List<CardDescriptor> getPlayerCards() {
        return this.Cards.stream()
                .map(x -> this.MapCards(x))
                .collect(Collectors.toList());
    }

    public void addCard(CardDescriptor cardDescriptor) {
        Card card = this.MapCards(cardDescriptor);
        Cards.add(card);
    }

    private CardDescriptor MapCards(Card card) {
        return new CardDescriptor(card.face, card.suit);
    }

    private Card MapCards(CardDescriptor card) {
        return new Card(card.face, card.suit);
    }
}