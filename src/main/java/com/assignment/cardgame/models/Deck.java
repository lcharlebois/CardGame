package com.assignment.cardgame.models;

import com.assignment.cardgame.common.Face;
import com.assignment.cardgame.common.Suit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="deckCards", joinColumns=@JoinColumn(name="deck_id"))
    List<Integer> Cards;

    Optional<Integer> GameId;

    public Deck() {
        Cards = BuildCards(52);
    }

    public int getId() {
        return id;
    }

    public List<CardDescriptor> GetCards(){
        List<CardDescriptor> cards = new ArrayList();
        for (int cardValue : this.Cards) {
            CardDescriptor card = BuildCard(cardValue);
            cards.add(card);
        }

        return cards;
    }

    public Optional<Integer> getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = Optional.of(gameId);
    }

    private List<Integer> BuildCards(int cardCount) {
        return Stream.iterate(0, x -> x + 1).limit(cardCount).collect(Collectors.toList());
    }

    private CardDescriptor BuildCard(int cardValue) {
        int face = cardValue % 13;
        int suit = cardValue / 13;

        return new CardDescriptor(Face.from(face), Suit.from(suit));
    }
}


