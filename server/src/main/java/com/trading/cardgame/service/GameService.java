package com.trading.cardgame.service;

import com.trading.cardgame.entity.Game;

public interface GameService
{
    Long saveGame(Game game);

    Game loadGame(Long id);

    Long createNewGame();

    void playCard(Long gameId, Long cardId);

    void skipTurn(long gameId);
}
