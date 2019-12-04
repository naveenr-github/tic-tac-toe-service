package com.game.tictactoe.tictactoeservice.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.State;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class StateRowMapper implements RowMapper<State> {

    @Override
    public State mapRow(ResultSet rs, int rowNum) throws SQLException {
        State result = new State();
        result.setId(rs.getLong("st_id"));
        result.setCode(rs.getString("st_code"));
        result.setTitle(rs.getString("st_title"));

        return result;
    }
}
