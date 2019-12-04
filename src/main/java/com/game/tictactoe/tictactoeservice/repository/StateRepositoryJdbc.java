package com.game.tictactoe.tictactoeservice.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.game.tictactoe.tictactoeservice.model.State;
import com.game.tictactoe.tictactoeservice.repository.StateRepository;
import com.game.tictactoe.tictactoeservice.repository.mappers.StateRowMapper;

/**
 * 
 * @author NA361613
 *
 */
@Repository
public class StateRepositoryJdbc implements StateRepository {

    public static final String COLUMNS = "st_id, st_code, st_title";
    public static final String QUERY_BASE = String.format("select %s from state", COLUMNS);
    private static final String SELECT_INITIAL_STATE = String.format("%s where st_code='IN_PROGRESS'", QUERY_BASE);
    private static final String SELECT_BY_CODE = String.format("%s where st_code=:code", QUERY_BASE);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StateRepositoryJdbc(
                    NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public State getInitialState() {
        return jdbcTemplate.queryForObject(SELECT_INITIAL_STATE, Collections.emptyMap(), new StateRowMapper());
    }

    @Override
    public State findByCode(String code) {
        return jdbcTemplate.queryForObject(SELECT_BY_CODE, new MapSqlParameterSource("code", code), new StateRowMapper());
    }
}
