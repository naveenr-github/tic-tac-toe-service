package com.game.tictactoe.tictactoeservice.repository;

import com.game.tictactoe.tictactoeservice.model.Location;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
public interface LocationRepository {
    Long add(Location location);

    List<Location> getTurnsByGameId(Long gameId);
}
