package com.assignment.cardgame;

import com.assignment.cardgame.controllers.DeckController;
import com.assignment.cardgame.controllers.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardgameApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardgameApplication.class, args);
	}
}
