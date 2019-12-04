package com.game.tictactoe.tictactoeservice.service;

import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.model.State;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
public interface GameService {
    List<Game> findAll();

    Game findById(Long id);

    Game addGame(String name, char character);

    State makeMove(Long gameId, Location location);
}
