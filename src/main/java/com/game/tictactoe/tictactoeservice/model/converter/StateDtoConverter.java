package com.game.tictactoe.tictactoeservice.model.converter;

import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.dto.StateDto;
import com.game.tictactoe.tictactoeservice.model.State;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class StateDtoConverter implements Converter<State, StateDto> {
    @Override
    public StateDto toDto(State state) {
        StateDto dto = new StateDto();
        dto.code = state.getCode();
        dto.title = state.getTitle();
        return dto;
    }
}
