package com.trading.cardgame.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trading.cardgame.dao.GameRepository;
import com.trading.cardgame.entity.Card;
import com.trading.cardgame.entity.Player;
import com.trading.cardgame.enums.GameStatus;
import com.trading.cardgame.enums.Turn;
import com.trading.cardgame.service.GameService;

import com.trading.cardgame.entity.Game;
import com.trading.cardgame.util.Constants;
import com.trading.cardgame.util.GameException;

@Service
public class GameServiceImpl implements GameService
{

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Long saveGame(Game game)
    {
        gameRepository.save(game);

        return game.getId();
    }

    @Override
    public Game loadGame(Long id)
    {
        return gameRepository.getOne(id);
    }

    @Override
    public Long createNewGame()
    {
        Game game = new Game();
        game.setStartDate(new Date());

        Player firstPlayer = new Player("firstPlayer");
        Player secondPlayer = new Player("secondPlayer");

        initializeHandAndDeck(firstPlayer);
        initializeHandAndDeck(secondPlayer);

        game.setFirstPlayer(firstPlayer);
        game.setSecondPlayer(secondPlayer);

        changeTurnOfGame(game);

        return saveGame(game);
    }

    @Override
    public void playCard(Long gameId, Long cardId)
    {
        Game game = loadGame(gameId);

        validateGame(game);

        if(cardId == null)
        {
            throw new GameException("invalid card");
        }

        if(!CollectionUtils.isEmpty(game.getPlayerByTurn().getHand()))
        {
            boolean cardFound = game.getPlayerByTurn().getHand().stream()
                    .anyMatch(card -> cardId.equals(card.getId()));

            if(!cardFound)
            {
                throw new GameException("card not found with Id : " + cardId);
            }

            for(Card card : game.getPlayerByTurn().getHand())
            {
                if(card.getId().equals(cardId))
                {
                    dealDamageWithCard(game, card);
                    game.getPlayerByTurn().getHand().remove(card);
                    break;
                }
            }
        }
        else
        {
            throw new GameException("card not found with Id : " + gameId);
        }

        checkGameIsEnded(game);

        if(!game.getStatus().equals(GameStatus.ENDED))
        {
            changeTurnOfGame(game);
        }

        saveGame(game);
    }

    @Override
    public void skipTurn(long gameId)
    {
        Game game = loadGame(gameId);

        validateGame(game);

        changeTurnOfGame(game);

        saveGame(game);
    }

    private void validateGame(Game game)
    {
        if(game == null)
        {
            throw new GameException("game not found with Id : ");
        }

        if(game.getStatus() != GameStatus.IN_PROGRESS)
        {
            throw new GameException("game already finished with Id : ");
        }
    }

    private void checkGameIsEnded(Game game)
    {
        if(game.getOtherPlayerByTurn().getHealth() <= 0)
        {
            game.setStatus(GameStatus.ENDED);
            game.setWinner(game.getTurn());
        }
    }

    private void dealDamageWithCard(Game game, Card card)
    {
        if(card.getManaCost() > game.getPlayerByTurn().getMana())
        {
            throw new GameException("not enough mana");
        }

        game.getOtherPlayerByTurn().dealDamage(card.getManaCost());
        game.getPlayerByTurn().removeMana(card.getManaCost());
    }

    private void changeTurnOfGame(Game game)
    {
        if(game.getTurn() == Turn.FIRST_PLAYER)
        {
            game.setTurn(Turn.SECOND_PLAYER);
        }
        else
        {
            game.setTurn(Turn.FIRST_PLAYER);
        }

        Card pickedCard = pickRandomCardFromDeck(game.getPlayerByTurn());

        if(pickedCard == null)
        {
            game.getPlayerByTurn().dealDamage(1);
            if(game.getPlayerByTurn().getHealth() <= 0)
            {
                game.setStatus(GameStatus.ENDED);
                game.setWinner(game.getNextTurn());
            }
        }
        else
        {
            if(game.getPlayerByTurn().getHand().size() <= 5)
            {
                game.getPlayerByTurn().getHand().add(pickedCard);
            }
        }

        game.getPlayerByTurn().addMana(1);
    }

    private Card pickRandomCardFromDeck(Player player)
    {
        if(CollectionUtils.isEmpty(player.getDeck()))
        {
            return null;
        }

        Random rand = new Random();
        return player.getDeck().remove(rand.nextInt(player.getDeck().size()));
    }

    public void initializeHandAndDeck(Player player)
    {
        Random rand = new Random();
        List<Card> cards = Constants.INITIAL_CARD_VALUES.stream()
                .map(value -> new Card(player, value))
                .collect(Collectors.toList());

        Set<Integer> randomIndexes = new HashSet<>();

        while(randomIndexes.size() < 3)
        {
            randomIndexes.add(rand.nextInt(cards.size()));
        }

        player.setHand(IntStream.range(0, cards.size()).boxed()
                .filter(randomIndexes::contains)
                .map(cards::get)
                .collect(Collectors.toList()));

        player.setDeck(IntStream.range(0, cards.size()).boxed()
                .filter(index -> !randomIndexes.contains(index))
                .map(cards::get)
                .collect(Collectors.toList()));

    }
}
