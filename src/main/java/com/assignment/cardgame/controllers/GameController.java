package com.assignment.cardgame.controllers;

import com.assignment.cardgame.models.Game;
import com.assignment.cardgame.services.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @PostMapping("/game")
    Game create(@RequestBody Game game){
        return gameRepository.save(game);
    }

    @GetMapping("/game")
    Iterable<Game> read(){
        return gameRepository.findAll();
    }

    @DeleteMapping("/game/{id}")
    void delete(@PathVariable Integer id){
        gameRepository.deleteById(id);
    }
}
