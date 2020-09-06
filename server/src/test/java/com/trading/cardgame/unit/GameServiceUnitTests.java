package com.trading.cardgame.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private Game game;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Mock
    private GameRepository gameRepository;

    @Test
    public void saveGameTest()
    {
        game = new Game();
        game.setId(11L);

        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Long id = gameServiceImpl.saveGame(game);

        Assert.assertEquals(id, game.getId());
    }

    @Test
    public void loadGameTest()
    {
        game = new Game();
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
        game = new Game();
        game.setId(11L);

        Mockito.when(gameRepository.save(game)).thenReturn(game);

        gameServiceImpl.saveGame(game);

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
        game = initializeGame();

        Mockito.when(gameRepository.getOne(11L)).thenReturn(game);

        gameServiceImpl.playCard(11L, 1L);

    }

    @Test(expected = GameException.class)
    public void playCardTestCardNotEnoughManaException()
    {
        game = initializeGame();

        Mockito.when(gameRepository.getOne(game.getId())).thenReturn(game);

        gameServiceImpl.playCard(game.getId(), 3L);

    }

    @Test
    public void playCardSuccessTest()
    {
        game = initializeGame();
        game.getPlayerByTurn().setMana(10);

        Mockito.when(gameRepository.getOne(game.getId())).thenReturn(game);

        gameServiceImpl.playCard(game.getId(), 3L);
    }

    @Test
    public void maximumNumberOfCardsAtHandIs5Test()
    {
        game = initializeGame(Turn.SECOND_PLAYER, 5);

        int initialMana = game.getFirstPlayer().getMana();

        Mockito.when(gameRepository.getOne(game.getId())).thenReturn(game);

        gameServiceImpl.skipTurn(game.getId());

        game = gameServiceImpl.loadGame(game.getId());

        Assert.assertEquals(game.getTurn(), Turn.FIRST_PLAYER);
        Assert.assertEquals(game.getPlayerByTurn().getMana(), initialMana + 1);
        Assert.assertEquals(game.getPlayerByTurn().getHand().size(), 5);
    }

    private Game initializeGame()
    {
        return initializeGame(Turn.FIRST_PLAYER, 1);
    }

    private Game initializeGame(Turn turn, int numberOfCards)
    {
        Game game = new Game();
        game.setId(11L);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setTurn(turn);
        game.setFirstPlayer(new Player());
        game.setSecondPlayer(new Player());
        Random rand = new Random();

        List<Card> hand = IntStream.range(0, numberOfCards).boxed()
                .map(n -> new Card(game.getPlayerByTurn(), rand.nextInt(8)))
                .collect(Collectors.toList());

        hand.get(0).setId(3L);

        game.getFirstPlayer().setHand(hand);
        game.getFirstPlayer().setDeck(new ArrayList<>());

        return game;
    }



}
