package com.game.tictactoe.tictactoeservice.repository.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.model.State;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author NA361613
 *
 */
public class GameBaseRowMapper implements RowMapper<Game> {

    private StateRowMapper stateRowMapper;

    public GameBaseRowMapper(StateRowMapper stateRowMapper) {
        this.stateRowMapper = stateRowMapper;
    }

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game result = new Game();
        result.setId(rs.getLong("gm_id"));
        result.setTitle(rs.getString("gm_title"));
        result.setName(rs.getString("gm_name"));
        result.setCharacter(rs.getString("gm_character").charAt(0));
        result.setState(stateRowMapper.mapRow(rs, rowNum));

        return result;
    }
}
