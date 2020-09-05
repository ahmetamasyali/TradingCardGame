package com.trading.cardgame.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "mana_cost")
    private int manaCost;

    @ManyToOne
    @JoinColumn(name="player_id", nullable=false)
    private Player player;

    public Card()
    {

    }

    public Card(Player player, int manaCost)
    {
        this.player = player;
        this.manaCost = manaCost;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getManaCost()
    {
        return manaCost;
    }

    public void setManaCost(int manaCost)
    {
        this.manaCost = manaCost;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
