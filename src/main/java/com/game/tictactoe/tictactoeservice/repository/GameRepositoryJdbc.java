package com.game.tictactoe.tictactoeservice.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.repository.GameRepository;
import com.game.tictactoe.tictactoeservice.repository.mappers.GameBriefRowMapper;
import com.game.tictactoe.tictactoeservice.repository.mappers.GameFullRowMapper;

/**
 * 
 * @author NA361613
 *
 */
@Repository
public class GameRepositoryJdbc implements GameRepository {
	Logger LOGGER = LoggerFactory.getLogger(GameRepositoryJdbc.class);

    public static final String COLUMNS_BASE = "gm_id, gm_title, gm_name, gm_character";
    public static final String COLUMNS_BRIEF = COLUMNS_BASE;
    public static final String COLUMNS_FULL = String.format("%s, gm_snapshot_id", COLUMNS_BASE);

    public static final String QUERY_BRIEF = String.format(
                    "select %s, %s from game join state on st_id = gm_state_id", COLUMNS_BRIEF,
                    StateRepositoryJdbc.COLUMNS);
    public static final String QUERY_FULL = String
                    .format("select %s, %s, %s, %s from game join state on st_id = gm_state_id join snapshot on sn_id = gm_snapshot_id left join location on lc_id = sn_last_turn_id",
                                    COLUMNS_FULL,
                                    StateRepositoryJdbc.COLUMNS,
                                    SnapshotRepositoryJdbc.COLUMNS,
                                    LocationRepositoryJdbc.COLUMNS);

    private static final String SELECT_ALL = QUERY_BRIEF;
    private static final String SELECT_BY_ID = String.format("%s where gm_id = :id", QUERY_FULL);
    private static final String INSERT_ADD = "insert into game (gm_title, gm_state_id, gm_name, gm_character, gm_snapshot_id) values (:title, :state_id, :name, :character, :snapshot_id)";
    private static final String UPDATE_ONE = "update game set gm_title = :title, gm_state_id = :state_id where gm_id = :id";
    // TODO: Move to separate repository
    private static final String INSERT_ADD_TURN = "insert into game_to_location (gl_game_id, gl_location_id) values (:gameId, :locationId)";

    private NamedParameterJdbcTemplate jdbcTemplate;

    private GameBriefRowMapper gameBriefRowMapper;
    private GameFullRowMapper gameFullRowMapper;

    @Autowired
    public GameRepositoryJdbc(
                    NamedParameterJdbcTemplate jdbcTemplate,
                    GameBriefRowMapper gameBriefRowMapper,
                    GameFullRowMapper gameFullRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.gameBriefRowMapper = gameBriefRowMapper;
        this.gameFullRowMapper = gameFullRowMapper;
    }

    @Override
    public List<Game> findAll() {
        return jdbcTemplate.query(SELECT_ALL, gameBriefRowMapper);
    }

    @Override
    public Game findById(Long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        Game game = null;
        try {
        	game = jdbcTemplate.queryForObject(SELECT_BY_ID, parameters, gameFullRowMapper);
        }catch (Exception e) {
        	LOGGER.error(" Error While Retrieving the Game Detaisls ", e.getMessage());
		}
        
        return game;
    }

    @Override
    public long add(Game game) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", game.getTitle());
        parameters.put("state_id", game.getState().getId());
        parameters.put("snapshot_id", game.getSnapshot().getId());
        parameters.put("name", game.getName());
        parameters.put("character", game.getCharacter());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_ADD, new MapSqlParameterSource(parameters), keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Game game) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", game.getId());
        parameters.put("title", game.getTitle());
        parameters.put("state_id", game.getState().getId());

        jdbcTemplate.update(UPDATE_ONE, new MapSqlParameterSource(parameters));
    }

    @Override
    public void addTurn(Long id, Long locationId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gameId", id);
        parameters.put("locationId", locationId);

        jdbcTemplate.update(INSERT_ADD_TURN, new MapSqlParameterSource(parameters));
    }
}
