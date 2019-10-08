package com.assignment.cardgame;

import com.assignment.cardgame.controllers.DeckController;
import com.assignment.cardgame.controllers.GameController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardgameApplicationTests {
	@Autowired
	GameController gameController;

	@Autowired
	DeckController deckController;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(gameController);
		Assert.assertNotNull(deckController);
	}

}
