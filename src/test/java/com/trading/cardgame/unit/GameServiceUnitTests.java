package com.trading.cardgame.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.trading.cardgame.dao.GameRepository;
import com.trading.cardgame.entity.Card;
import com.trading.cardgame.entity.Game;
import com.trading.cardgame.entity.Player;
import com.trading.cardgame.enums.GameStatus;
import com.trading.cardgame.enums.Turn;
import com.trading.cardgame.service.impl.GameServiceImpl;
import com.trading.cardgame.util.Constants;
import com.trading.cardgame.util.GameException;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceUnitTests
{
    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Mock
    private GameRepository gameRepository;

    @Test
    public void saveGameTest()
    {
        Game game = new Game();
        game.setId(11L);

        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Long id = gameServiceImpl.saveGame(game);

        Assert.assertEquals(id, game.getId());
    }

    @Test
    public void loadGameTest()
    {
        Game game = new Game();
        game.setId(11L);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setTurn(Turn.SECOND_PLAYER);

        Mockito.when(gameRepository.getOne(11L)).thenReturn(game);

        Game loadedGame = gameServiceImpl.loadGame(11L);

        Assert.assertEquals(11, loadedGame.getId().longValue());
        Assert.assertEquals(GameStatus.IN_PROGRESS, loadedGame.getStatus());
        Assert.assertEquals(Turn.SECOND_PLAYER, loadedGame.getTurn());
    }


    @Test
    public void createNewGameTest()
    {
        Game game = new Game();
        game.setId(11L);

        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Long id = gameServiceImpl.saveGame(game);

        Assert.assertEquals(11, game.getId().longValue());
    }

    @Test
    public void initializeHandAndDeckTest()
    {
        Player player = new Player();

        gameServiceImpl.initializeHandAndDeck(player);

        Assert.assertEquals(player.getDeck().size(), 17);
        Assert.assertEquals(player.getHand().size(), 3);

        player.getDeck()
                .forEach( card -> Assert.assertTrue( Constants.INITIAL_CARD_VALUES.contains(card.getManaCost())));

    }

    @Test(expected = GameException.class)
    public void playCardTestCardNotFountException()
    {
        Game game = initializeGame();

        Mockito.when(gameRepository.getOne(11L)).thenReturn(game);

        gameServiceImpl.playCard(11L, 1L);

    }

    @Test(expected = GameException.class)
    public void playCardTestCardNotEnoughManaException()
    {
        Game game = initializeGame();

        Mockito.when(gameRepository.getOne(11L)).thenReturn(game);

        gameServiceImpl.playCard(11L, 3L);

    }

    @Test
    public void playCardTest()
    {
        Game game = initializeGame();
        game.getPlayerByTurn().setMana(10);

        Mockito.when(gameRepository.getOne(11L)).thenReturn(game);

        gameServiceImpl.playCard(11L, 3L);

    }

    private Game initializeGame()
    {
        Game game = new Game();
        game.setId(11L);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setTurn(Turn.FIRST_PLAYER);
        game.setFirstPlayer(new Player());
        game.setSecondPlayer(new Player());

        List<Card> hand = new ArrayList<>();

        Card cardAtHand = new Card(game.getPlayerByTurn(), 3);
        cardAtHand.setId(3L);

        hand.add(cardAtHand);

        game.getPlayerByTurn().setHand(hand);
        game.getPlayerByTurn().setDeck(new ArrayList<>());

        return game;
    }



}
