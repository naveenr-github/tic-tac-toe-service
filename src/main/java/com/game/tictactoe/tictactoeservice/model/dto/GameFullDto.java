package com.game.tictactoe.tictactoeservice.model.dto;

/**
 * 
 * @author NA361613
 *
 */
public class GameFullDto extends GameBaseDto {
	
    public StateDto state;

    public LocationDto lastTurn;

    // TODO: Configure converty char[][] to 2-dimensional array in json. Now it is converted to String[]
    public String[][] snapshot;
}
