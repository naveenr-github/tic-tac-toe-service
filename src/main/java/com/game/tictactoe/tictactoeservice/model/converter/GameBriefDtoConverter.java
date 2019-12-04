package com.game.tictactoe.tictactoeservice.model.converter;

import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.dto.GameBriefDto;
import com.game.tictactoe.tictactoeservice.model.Game;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class GameBriefDtoConverter implements Converter<Game, GameBriefDto> {

    @Override
    public GameBriefDto toDto(Game game) {
        GameBriefDto dto = new GameBriefDto();
        dto.id = game.getId();
        dto.stateCode = game.getState().getCode();
        dto.title = game.getTitle();
        dto.character = game.getCharacter();
        dto.name = game.getName();
        return dto;
    }

}
