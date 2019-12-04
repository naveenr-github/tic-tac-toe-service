package com.game.tictactoe.tictactoeservice.model;

/**
 * 
 * @author NA361613
 *
 */
public class Game {
    private Long id;

    private State state;

    private String title;
    
    private String name;
    
    private char character;

    private Snapshot snapshot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
        

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }
}
