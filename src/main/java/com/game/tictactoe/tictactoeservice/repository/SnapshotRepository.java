package com.game.tictactoe.tictactoeservice.repository;

import com.game.tictactoe.tictactoeservice.model.Snapshot;

/**
 * 
 * @author NA361613
 *
 */
public interface SnapshotRepository {

    Long add(Snapshot snapshot);

    void update(Snapshot snapshot);
}
