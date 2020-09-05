package com.trading.cardgame.integration;

import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.trading.cardgame.controller.GameController;
import com.trading.cardgame.views.CardView;
import com.trading.cardgame.views.GameView;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class CardGameApplicationTests
{
    @Autowired
    private GameController gameController;


    @Test
    public void playGameTest()
    {
        Long gameId = gameController.createNewGame();

        GameView game = gameController.loadGame(gameId);

        Assert.assertNotNull(game);
        Assert.assertNotNull(game.getFirstPlayerDeck());
        Assert.assertNotNull(game.getFirstPlayerHand());
        Assert.assertNotNull(game.getSecondPlayerDeck());
        Assert.assertNotNull(game.getSecondPlayerHand());

        checkHealthAndMana(gameId, 30, 30, 1, 0);

        IntStream.range(0, 20).boxed()
                .forEach(i -> gameController.skipTurn(gameId));


        checkHealthAndMana(gameId, 30, 30, 10, 10);

        IntStream.range(0, 2).boxed()
                .forEach(i -> gameController.skipTurn(gameId));

        checkHealthAndMana(gameId, 30, 30, 10, 10); // max mana is 10

        CardView card = findCardFromHand(gameId);
        gameController.playCard(gameId, card.getCardId());

        checkHealthAndMana(gameId, 30, 30 - card.getManaCost(), 10 - card.getManaCost(), 10);
        gameController.skipTurn(gameId);
    }

    private void checkHealthAndMana(Long gameId, int firstHealt, int secondHealth, int firstMana, int secondMana)
    {
        GameView game = gameController.loadGame(gameId);

        Assert.assertEquals(game.getFirstPlayerHealth(), firstHealt);
        Assert.assertEquals(game.getSecondPlayerHealth(), secondHealth);

        Assert.assertEquals(game.getFirstPlayerMana(), firstMana);
        Assert.assertEquals(game.getSecondPlayerMana(), secondMana);

    }

    private CardView findCardFromHand(Long gameId)
    {
        GameView game = gameController.loadGame(gameId);
        return game.getFirstPlayerHand().get(0);
    }
}
