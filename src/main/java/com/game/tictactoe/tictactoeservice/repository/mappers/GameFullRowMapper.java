package com.game.tictactoe.tictactoeservice.repository.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class GameFullRowMapper extends GameBaseRowMapper {

    private SnapshotRowMapper snapshotRowMapper;

    @Autowired
    public GameFullRowMapper(StateRowMapper stateRowMapper,
                             SnapshotRowMapper snapshotRowMapper) {
        super(stateRowMapper);
        this.snapshotRowMapper = snapshotRowMapper;
    }

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game result = super.mapRow(rs, rowNum);

        result.setSnapshot(snapshotRowMapper.mapRow(rs, rowNum));

        return result;
    }
}
