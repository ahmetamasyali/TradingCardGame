package com.trading.cardgame.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trading.cardgame.util.Constants;

@Entity
@Table(name = "player")
public class Player
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "health")
    private int health = Constants.INITIAL_HEALTH;

    @Column(name = "mana")
    private int mana = Constants.INITIAL_MANA;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="player_decks",
            joinColumns = @JoinColumn( name="player_id"),
            inverseJoinColumns = @JoinColumn( name="card_id"))
    private List<Card> deck;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="player_hands",
            joinColumns = @JoinColumn( name="player_id"),
            inverseJoinColumns = @JoinColumn( name="card_id"))
    private List<Card> hand;

    public Player()
    {

    }

    public void dealDamage(int damage)
    {
        this.health -= damage;
    }

    public void addMana(int mana)
    {
        this.mana += mana;

        if(this.mana > Constants.MAXIMUM_MANA)
            this.mana = Constants.MAXIMUM_MANA;
    }

    public void removeMana(int mana)
    {
        this.mana -= mana;
    }

    public Player(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public List<Card> getDeck()
    {
        return deck;
    }

    public void setDeck(List<Card> deck)
    {
        this.deck = deck;
    }

    public List<Card> getHand()
    {
        return hand;
    }

    public void setHand(List<Card> hand)
    {
        this.hand = hand;
    }
}
