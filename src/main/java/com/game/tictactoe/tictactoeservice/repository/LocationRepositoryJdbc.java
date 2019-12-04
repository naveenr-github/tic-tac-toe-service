package com.game.tictactoe.tictactoeservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.repository.LocationRepository;
import com.game.tictactoe.tictactoeservice.repository.mappers.LocationRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author NA361613
 *
 */
@Repository
public class LocationRepositoryJdbc implements LocationRepository {

    public static final String COLUMNS = "lc_id, lc_x, lc_y";
    public static final String QUERY_BASE = String.format("select %s from location", COLUMNS);

    public  static final String INSERT_ADD = "insert into location (lc_x, lc_y) values (:x, :y)";
    public  static final String SELECT_BY_GAME_ID = String.format("%s join game_to_location on gl_location_id = lc_id where gl_game_id = :gameId", QUERY_BASE);

    private NamedParameterJdbcTemplate jdbcTemplate;

    private LocationRowMapper locationRowMapper;

    @Autowired
    public LocationRepositoryJdbc(NamedParameterJdbcTemplate jdbcTemplate,
                                  LocationRowMapper locationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.locationRowMapper = locationRowMapper;
    }

    @Override
    public Long add(Location location) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("x", location.getX());
        parameters.put("y", location.getY());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_ADD, new MapSqlParameterSource(parameters), keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Location> getTurnsByGameId(Long gameId) {
        return jdbcTemplate.query(SELECT_BY_GAME_ID, new MapSqlParameterSource("gameId", gameId), locationRowMapper);
    }
}
