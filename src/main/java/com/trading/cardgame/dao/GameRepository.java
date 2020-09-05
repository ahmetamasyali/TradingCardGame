package com.trading.cardgame.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trading.cardgame.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>
{

}
