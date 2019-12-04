package com.game.tictactoe.tictactoeservice.model.converter;

/**
 * 
 * @author NA361613
 *
 * @param <MODEL>
 * @param <DTO>
 */
public interface Converter<MODEL, DTO> {

    default DTO toDto(MODEL model) {
        throw new UnsupportedOperationException("Not implemented");
    }

    default MODEL fromDto(DTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
