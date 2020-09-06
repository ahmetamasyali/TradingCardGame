package com.trading.cardgame.views;

import com.trading.cardgame.entity.Card;

public class CardView
{
    private long cardId;
    private int manaCost;

    public CardView(Card card)
    {
        this.cardId = card.getId();
        this.manaCost = card.getManaCost();
    }

    public long getCardId()
    {
        return cardId;
    }

    public void setCardId(long cardId)
    {
        this.cardId = cardId;
    }

    public int getManaCost()
    {
        return manaCost;
    }

    public void setManaCost(int manaCost)
    {
        this.manaCost = manaCost;
    }
}
