package com.assignment.cardgame.controllers;

import com.assignment.cardgame.services.Dtos.GameDto;
import com.assignment.cardgame.services.GameManagementService;
import com.assignment.cardgame.services.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameManagementService gameManagementService;

    @PostMapping("/games")
    public GameDto create(){
        return this.gameManagementService.createGame();
    }

    @GetMapping("/games")
    public List<GameDto> getAll(){
        return this.gameManagementService.getAllGames();
    }

    @DeleteMapping("/games/{id}")
    public void delete(@PathVariable Integer id) throws EntityNotFoundException {
        this.gameManagementService.deleteGame(id);
    }

    @PostMapping("/games/{id}/decks")
    public GameDto addDeck(@PathVariable Integer id, @RequestBody DeckParameter deckParameter) throws EntityNotFoundException {
        return this.gameManagementService.AddDeckToGame(id, deckParameter.id);
    }

//
//    @PostMapping("/decks/{id}")
//    public ResponseEntity dealCard(@PathVariable Integer id){
//        Optional<Deck> optionalDeck = deckRepository.findById(id);
//        if (optionalDeck.isPresent()){
//            Deck deck = optionalDeck.get();
//            CardDescriptor card = deck.DealCard();
//            if (card != null){
//                deckRepository.save(deck);
//                return new ResponseEntity(card.toString(), HttpStatus.OK);
//            }
//        }
//
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//    }
}
