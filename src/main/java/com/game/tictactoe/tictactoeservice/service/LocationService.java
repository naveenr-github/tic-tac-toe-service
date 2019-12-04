package com.game.tictactoe.tictactoeservice.service;

import com.game.tictactoe.tictactoeservice.model.Location;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
public interface LocationService {
    List<Location> getTurnHistory(Long gameId);
}
