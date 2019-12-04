package com.game.tictactoe.tictactoeservice.repository;

/**
 * 
 * @author NA361613
 *
 */
public interface RepositoryConstants {
    int BOARD_SIZE_WIDTH = 3;
    int BOARD_SIZE_HEIGHT = 3;
    int BOARD_WIN_LINE_LENGTH = 3;

    char BOARD_EMPTY_CELL = ' ';
    char BOARD_X_CELL = 'x';
    char BOARD_O_CELL = 'o';

    String STATE_CODE_IN_PROGRESS = "IN_PROGRESS";
    String STATE_CODE_DRAW = "DRAW";
    String STATE_CODE_X_WON = "X_WON";
    String STATE_CODE_O_WON = "O_WON";
    String GAME_NOT_AVAILABLE = "NOT_AVAILABLE";
}
