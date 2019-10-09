package com.assignment.cardgame.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="deckCards", joinColumns=@JoinColumn(name="deck_id"))
    List<Integer> cards;

    public Deck() {
        cards = buildCards(52);
    }

    public int getId() {
        return id;
    }

    public List<CardDescriptor> getCards(){
        List<CardDescriptor> cards = new ArrayList();
        for (int cardValue : this.cards) {
            CardDescriptor card = buildCard(cardValue);
            cards.add(card);
        }

        return cards;
    }

    private List<Integer> buildCards(int cardCount) {
        return Stream.iterate(0, x -> x + 1).limit(cardCount).collect(Collectors.toList());
    }

    private CardDescriptor buildCard(int cardValue) {
        Card card = Card.fromCardValue(cardValue);

        return new CardDescriptor(card.face, card.suit);
    }
}


