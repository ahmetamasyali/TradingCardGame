package com.trading.cardgame.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.trading.cardgame.enums.GameStatus;
import com.trading.cardgame.enums.Turn;

@Entity
@Table(name = "game")
public class Game
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "status")
    private GameStatus status = GameStatus.IN_PROGRESS;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_player_id")
    private Player firstPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_player_id")
    private Player secondPlayer;

    @Column(name = "turn")
    private Turn turn = Turn.SECOND_PLAYER;

    @Column(name = "winner")
    private Turn winner;

    public Turn getNextTurn()
    {
        if(Turn.FIRST_PLAYER == this.turn)
        {
            return Turn.SECOND_PLAYER;
        }

        return Turn.FIRST_PLAYER;
    }

    public Player getPlayerByTurn()
    {
        if(Turn.FIRST_PLAYER == this.turn)
        {
            return firstPlayer;
        }

        return secondPlayer;
    }

    public Player getOtherPlayerByTurn()
    {
        if(Turn.FIRST_PLAYER == this.turn)
        {
            return secondPlayer;
        }

        return firstPlayer;
    }

    public boolean isGameFinished()
    {
        return this.status == GameStatus.ENDED;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public GameStatus getStatus()
    {
        return status;
    }

    public void setStatus(GameStatus status)
    {
        this.status = status;
    }

    public Player getFirstPlayer()
    {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer)
    {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer()
    {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer)
    {
        this.secondPlayer = secondPlayer;
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
}
