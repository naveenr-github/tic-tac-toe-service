package com.game.tictactoe.tictactoeservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.Snapshot;
import com.game.tictactoe.tictactoeservice.repository.SnapshotRepository;
import com.game.tictactoe.tictactoeservice.repository.converters.DumpConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class SnapshotRepositoryJdbc implements SnapshotRepository {
    public static final String COLUMNS = "sn_id, sn_last_turn_id, sn_dump";
//    public static final String QUERY_BASE = String.format("select %s from lc_location", COLUMNS);

    public static final String INSERT_ADD = "insert into snapshot (sn_dump) values (:dump)";
    public static final String UPDATE_ONE = "update snapshot set sn_dump = :dump, sn_last_turn_id = :last_turn_id where sn_id = :id";


    private NamedParameterJdbcTemplate jdbcTemplate;

    private DumpConverter dumpConverter;

    @Autowired
    public SnapshotRepositoryJdbc(NamedParameterJdbcTemplate jdbcTemplate,
                                  DumpConverter dumpConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.dumpConverter = dumpConverter;
    }

    @Override
    public Long add(Snapshot snapshot) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dump", dumpConverter.toStorage(snapshot.getDump()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_ADD, new MapSqlParameterSource(parameters), keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Snapshot snapshot) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", snapshot.getId());
        parameters.put("dump", dumpConverter.toStorage(snapshot.getDump()));
        Long lastTurnId = snapshot.getLastTurn() == null ? null : snapshot.getLastTurn().getId();
        parameters.put("last_turn_id", lastTurnId);

        jdbcTemplate.update(UPDATE_ONE, new MapSqlParameterSource(parameters));
    }
}
