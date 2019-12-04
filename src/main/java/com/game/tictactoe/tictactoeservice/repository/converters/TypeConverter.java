package com.game.tictactoe.tictactoeservice.repository.converters;

/**
 * 
 * @author NA361613
 *
 * @param <STORAGE>
 * @param <MODEL>
 */
public interface TypeConverter<STORAGE, MODEL> {
    STORAGE toStorage(MODEL source);
    MODEL toModel(STORAGE source);
}
