package com.trading.cardgame.views;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.trading.cardgame.entity.Game;
import com.trading.cardgame.enums.GameStatus;
import com.trading.cardgame.enums.Turn;

public class GameView implements Serializable
{
    private static final long serialVersionUID = -3976250833190453921L;

    private Turn turn;
    private Turn winner;

    private GameStatus status;

    private int firstPlayerHealth;
    private int firstPlayerMana;

    private int secondPlayerHealth;
    private int secondPlayerMana;

    private List<CardView> firstPlayerHand;
    private List<CardView> secondPlayerHand;

    private List<CardView> firstPlayerDeck;
    private List<CardView> secondPlayerDeck;

    public GameView(Game game)
    {
        this.turn = game.getTurn();
        this.winner = game.getWinner();
        this.status = game.getStatus();
        this.firstPlayerHealth = game.getFirstPlayer().getHealth();
        this.firstPlayerMana = game.getFirstPlayer().getMana();
        this.secondPlayerHealth = game.getSecondPlayer().getHealth();
        this.secondPlayerMana = game.getSecondPlayer().getMana();

        this.firstPlayerDeck = game.getFirstPlayer().getDeck().stream()
                .map(CardView::new)
                .collect(Collectors.toUnmodifiableList());

        this.firstPlayerHand = game.getFirstPlayer().getHand().stream()
                .map(CardView::new)
                .collect(Collectors.toUnmodifiableList());

        this.secondPlayerDeck = game.getSecondPlayer().getDeck().stream()
                .map(CardView::new)
                .collect(Collectors.toUnmodifiableList());

        this.secondPlayerHand = game.getSecondPlayer().getHand().stream()
                .map(CardView::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Turn getTurn()
    {
        return turn;
    }

    public void setTurn(Turn turn)
    {
        this.turn = turn;
    }

    public Turn getWinner()
    {
        return winner;
    }

    public void setWinner(Turn winner)
    {
        this.winner = winner;
    }

    public GameStatus getStatus()
    {
        return status;
    }

    public void setStatus(GameStatus status)
    {
        this.status = status;
    }

    public int getFirstPlayerHealth()
    {
        return firstPlayerHealth;
    }

    public void setFirstPlayerHealth(int firstPlayerHealth)
    {
        this.firstPlayerHealth = firstPlayerHealth;
    }

    public int getFirstPlayerMana()
    {
        return firstPlayerMana;
    }

    public void setFirstPlayerMana(int firstPlayerMana)
    {
        this.firstPlayerMana = firstPlayerMana;
    }

    public int getSecondPlayerHealth()
    {
        return secondPlayerHealth;
    }

    public void setSecondPlayerHealth(int secondPlayerHealth)
    {
        this.secondPlayerHealth = secondPlayerHealth;
    }

    public int getSecondPlayerMana()
    {
        return secondPlayerMana;
    }

    public void setSecondPlayerMana(int secondPlayerMana)
    {
        this.secondPlayerMana = secondPlayerMana;
    }

    public List<CardView> getFirstPlayerHand()
    {
        return firstPlayerHand;
    }

    public void setFirstPlayerHand(List<CardView> firstPlayerHand)
    {
        this.firstPlayerHand = firstPlayerHand;
    }

    public List<CardView> getSecondPlayerHand()
    {
        return secondPlayerHand;
    }

    public void setSecondPlayerHand(List<CardView> secondPlayerHand)
    {
        this.secondPlayerHand = secondPlayerHand;
    }

    public List<CardView> getFirstPlayerDeck()
    {
        return firstPlayerDeck;
    }

    public void setFirstPlayerDeck(List<CardView> firstPlayerDeck)
    {
        this.firstPlayerDeck = firstPlayerDeck;
    }

    public List<CardView> getSecondPlayerDeck()
    {
        return secondPlayerDeck;
    }

    public void setSecondPlayerDeck(List<CardView> secondPlayerDeck)
    {
        this.secondPlayerDeck = secondPlayerDeck;
    }
}
