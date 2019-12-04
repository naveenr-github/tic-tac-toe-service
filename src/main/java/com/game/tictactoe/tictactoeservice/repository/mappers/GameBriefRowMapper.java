package com.game.tictactoe.tictactoeservice.repository.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class GameBriefRowMapper extends GameBaseRowMapper {

    @Autowired
    public GameBriefRowMapper(StateRowMapper stateRowMapper) {
        super(stateRowMapper);
    }
}
