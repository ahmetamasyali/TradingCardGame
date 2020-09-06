package com.trading.cardgame.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trading.cardgame.service.GameService;

import com.trading.cardgame.util.GameException;
import com.trading.cardgame.util.JsonResponse;
import com.trading.cardgame.views.GameView;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GameController
{
    @Autowired
    private GameService gameService;

    @PostMapping("/createNewGame")
    public Long createNewGame()
    {
        return gameService.createNewGame();
    }


    @GetMapping("/{gameId}")
    public GameView loadGame(@PathVariable("gameId") long gameId) {
        return new GameView(gameService.loadGame(gameId));
    }

    @PostMapping("/{gameId}/play/{cardId}")
    public void playCard(@PathVariable("gameId") long gameId, @PathVariable("cardId") long cardId) {
        gameService.playCard(gameId, cardId);
    }

    @PostMapping("/{gameId}/skip")
    public void skipTurn(@PathVariable("gameId") long gameId) {
        gameService.skipTurn(gameId);
    }

    @ExceptionHandler(GameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResponse handleError(GameException e) {

        return new JsonResponse(e.getMessage());
    }
}
