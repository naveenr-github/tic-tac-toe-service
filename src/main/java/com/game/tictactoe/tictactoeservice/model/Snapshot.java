package com.game.tictactoe.tictactoeservice.model;

/**
 * 
 * @author NA361613
 *
 */
public class Snapshot {
    private Long id;
    
    private Location lastTurn;
    
    private char[][] dump;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(Location lastTurn) {
        this.lastTurn = lastTurn;
    }

    public char[][] getDump() {
        return dump;
    }

    public void setDump(char[][] dump) {
        this.dump = dump;
    }
}
