package com.assignment.cardgame.controllers;

import com.assignment.cardgame.models.Deck;
import com.assignment.cardgame.repositories.DeckRepository;
import com.assignment.cardgame.services.Dtos.DeckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class DeckController {

    @Autowired
    DeckRepository deckRepository;

    @PostMapping("/decks")
    public DeckDto create(){
        Deck deck = deckRepository.save(new Deck());
        return this.MapDeck(deck);
    }

    @GetMapping("/decks")
    public List<DeckDto> read(){
        Iterable<Deck> games = deckRepository.findAll();
        return StreamSupport.stream(games.spliterator(), false)
                .map(x -> this.MapDeck(x))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        if (deckRepository.existsById(id)){
            deckRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/decks/{id}")
    public ResponseEntity get(@PathVariable Integer id){
        Optional<Deck> deck = deckRepository.findById(id);
        if (deck.isPresent()){
            return new ResponseEntity(MapDeck(deck.get()), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private DeckDto MapDeck(Deck deck) {
        List<String> cards = deck.getCards().stream().map(x -> x.toString()).collect(Collectors.toList());
        return new DeckDto(deck.getId(), cards);
    }
}
