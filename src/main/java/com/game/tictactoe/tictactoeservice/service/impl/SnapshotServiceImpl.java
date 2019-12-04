package com.game.tictactoe.tictactoeservice.service.impl;

import org.springframework.stereotype.Service;
import com.game.tictactoe.tictactoeservice.model.Snapshot;
import com.game.tictactoe.tictactoeservice.repository.RepositoryConstants;
import com.game.tictactoe.tictactoeservice.service.SnapshotService;

import java.util.Arrays;

/**
 * 
 * @author NA361613
 *
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {
    @Override
    public Snapshot createInitialSnapshot() {
        Snapshot result = new Snapshot();
        result.setDump(buildInitialDump());
        return result;
    }

    private char[][] buildInitialDump() {
        char[][] dump = new char[RepositoryConstants.BOARD_SIZE_HEIGHT][RepositoryConstants.BOARD_SIZE_WIDTH];
        for (char[] row : dump) {
            Arrays.fill(row, RepositoryConstants.BOARD_EMPTY_CELL);
        }
        return dump;
    }
}
