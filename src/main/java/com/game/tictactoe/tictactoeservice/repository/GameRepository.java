package com.game.tictactoe.tictactoeservice.repository;

import com.game.tictactoe.tictactoeservice.model.Game;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
public interface GameRepository {
    List<Game> findAll();

    Game findById(Long id);

    long add(Game game);

    void update(Game game);

    void addTurn(Long id, Long locationId);
}
