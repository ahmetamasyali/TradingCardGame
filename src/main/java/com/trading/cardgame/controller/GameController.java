package com.trading.cardgame.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.cardgame.service.GameService;

import com.trading.cardgame.views.GameView;

@RestController
public class GameController
{
    @Autowired
    private GameService gameService;

    @GetMapping("/createNewGame")
    public Long createNewGame() {

       return gameService.createNewGame();
    }

    @GetMapping("/{gameId}")
    public GameView loadGame(@PathVariable("gameId") long gameId) {
        return new GameView(gameService.loadGame(gameId));
    }

    //TODO
    @GetMapping("/{gameId}/play/{cardId}")
    public void playCard(@PathVariable("gameId") long gameId, @PathVariable("cardId") long cardId) {
        gameService.playCard(gameId, cardId);
    }

    //TODO
    @GetMapping("/{gameId}/skip")
    public void skipTurn(@PathVariable("gameId") long gameId) {
        gameService.skipTurn(gameId);
    }
}
