package com.game.tictactoe.tictactoeservice.repository;

import com.game.tictactoe.tictactoeservice.model.State;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
public interface StateRepository {

    State getInitialState();

    State findByCode(String code);
}
