package com.game.tictactoe.tictactoeservice.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class LocationRowMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location result = new Location();
        result.setId(rs.getLong("lc_id"));
        result.setX(rs.getInt("lc_x"));
        result.setY(rs.getInt("lc_y"));

        return result;
    }
}
