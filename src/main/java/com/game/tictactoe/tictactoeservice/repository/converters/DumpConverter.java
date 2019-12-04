package com.game.tictactoe.tictactoeservice.repository.converters;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.repository.RepositoryConstants;

import java.lang.reflect.Type;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class DumpConverter implements TypeConverter<String, char[][]> {
    @Override
    public String toStorage(char[][] source) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] row : source) {
            for (char ch : row) {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public char[][] toModel(String source) {
        if (source.length() != RepositoryConstants.BOARD_SIZE_WIDTH * RepositoryConstants.BOARD_SIZE_HEIGHT) {
            throw new IllegalArgumentException("Invalid game snapshot");
        }
        char[][] result = new char[RepositoryConstants.BOARD_SIZE_HEIGHT][RepositoryConstants.BOARD_SIZE_WIDTH];
        char[] snapshotAsArray = source.toCharArray();
        for (int y = 0; y < RepositoryConstants.BOARD_SIZE_HEIGHT; y++) {
            result[y] = new char[RepositoryConstants.BOARD_SIZE_WIDTH];
            for (int x = 0; x < RepositoryConstants.BOARD_SIZE_WIDTH; x++) {
                result[y][x] = snapshotAsArray[y * RepositoryConstants.BOARD_SIZE_WIDTH + x];
            }
        }
        return result;
    }
}
