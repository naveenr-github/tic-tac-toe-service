package com.game.tictactoe.tictactoeservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.model.Snapshot;
import com.game.tictactoe.tictactoeservice.model.State;
import com.game.tictactoe.tictactoeservice.repository.GameRepository;
import com.game.tictactoe.tictactoeservice.repository.LocationRepository;
import com.game.tictactoe.tictactoeservice.repository.RepositoryConstants;
import com.game.tictactoe.tictactoeservice.repository.SnapshotRepository;
import com.game.tictactoe.tictactoeservice.repository.StateRepository;
import com.game.tictactoe.tictactoeservice.service.GameService;
import com.game.tictactoe.tictactoeservice.service.SnapshotService;

import java.util.List;


/**
 * 
 * @author NA361613
 *
 */
@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private StateRepository stateRepository;
    private SnapshotService snapshotService;
    private LocationRepository locationRepository;
    private SnapshotRepository snapshotRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           StateRepository stateRepository,
                           SnapshotService snapshotService,
                           LocationRepository locationRepository,
                           SnapshotRepository snapshotRepository) {
        this.gameRepository = gameRepository;
        this.stateRepository = stateRepository;
        this.snapshotService = snapshotService;
        this.locationRepository = locationRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game addGame(String name, char character) {
        State initialState = stateRepository.getInitialState();

        Game game = new Game();
        game.setState(initialState);
        game.setTitle("TicTacToe Game");
        game.setName(name);
        game.setCharacter(character);

        Snapshot snapshot = snapshotService.createInitialSnapshot();
        Long snapshotId = snapshotRepository.add(snapshot);
        snapshot.setId(snapshotId);
        game.setSnapshot(snapshot);

        long newId = gameRepository.add(game);
        game.setId(newId);

        return game;
    }

    @Override
    public State makeMove(Long gameId, Location location) {
        Game game = gameRepository.findById(gameId);
        
        State newState = new State();
        
        //If game is not there for the given game Id.
        if(null == game) {
        	newState.setCode(RepositoryConstants.GAME_NOT_AVAILABLE);
        	newState.setTitle("Game Not Available");        
        }
        else {
        	newState = getState(game, location);
            
        }
        
        return newState;
    }

    private State getState(Game game, Location location) {
    	State newState = new State();
    	
    	Snapshot snapshot = game.getSnapshot();
        char[][] dump = snapshot.getDump();        
       
        if(dump[location.getY()][location.getX()] != 0 && 
        		(dump[location.getY()][location.getX()] =='x' 
        		|| dump[location.getY()][location.getX()] == 'o')) {
        	
        	newState.setTitle("Position Already Occupied");
        	newState.setCode(game.getState().getCode());
        	
        }
        else {
        	dump[location.getY()][location.getX()] = getNextTurn(dump, snapshot.getLastTurn());

            Long locationId = locationRepository.add(location);
            location.setId(locationId);
            snapshot.setLastTurn(location);

            gameRepository.addTurn(game.getId(), locationId);
            snapshotRepository.update(snapshot);

             newState = calculateState(dump, location);
            if (!newState.getCode().equals(game.getState().getCode())) {
                game.setState(newState);
                gameRepository.update(game);
            }
            
        }
    	return newState;
    }
    
    private char getNextTurn(char[][] dump, Location lastTurn) {
        char result = RepositoryConstants.BOARD_X_CELL;
        if (lastTurn != null && dump[lastTurn.getY()][lastTurn.getX()] == RepositoryConstants.BOARD_X_CELL) {
            result = RepositoryConstants.BOARD_O_CELL;
        }
        return result;
    }

    private State calculateState(char[][] dump, Location lastTurn) {

        char lastPlacedSymbol = dump[lastTurn.getY()][lastTurn.getX()];

        int emptyCellsCount = getEmptyCellsCount(dump);
        if (emptyCellsCount == 0) {
            return stateRepository.findByCode(RepositoryConstants.STATE_CODE_DRAW);
        }

        int horizontalFilledCount = getHorizontalFilledCellsCount(dump, lastTurn, lastPlacedSymbol);
        if (horizontalFilledCount == RepositoryConstants.BOARD_WIN_LINE_LENGTH) {
            return stateRepository.findByCode(getStateCodeByLastPlacedSymbol(lastPlacedSymbol));
        }

        int verticalFilledCount = getVerticalFilledCellsCount(dump, lastTurn, lastPlacedSymbol);
        if (verticalFilledCount == RepositoryConstants.BOARD_WIN_LINE_LENGTH) {
            return stateRepository.findByCode(getStateCodeByLastPlacedSymbol(lastPlacedSymbol));
        }

        int mainDiagonalFilledCount = getMainDiagonalFilledCellsCount(dump, lastPlacedSymbol);
        if (mainDiagonalFilledCount == RepositoryConstants.BOARD_WIN_LINE_LENGTH) {
            return stateRepository.findByCode(getStateCodeByLastPlacedSymbol(lastPlacedSymbol));
        }

        int secondDiagonalFilledCount = getSecondDiagonalFilledCellsCount(dump, lastPlacedSymbol);
        if (secondDiagonalFilledCount == RepositoryConstants.BOARD_WIN_LINE_LENGTH) {
            return stateRepository.findByCode(getStateCodeByLastPlacedSymbol(lastPlacedSymbol));
        }

        return stateRepository.findByCode(RepositoryConstants.STATE_CODE_IN_PROGRESS);
    }

    private int getSecondDiagonalFilledCellsCount(char[][] dump, char lastPlacedSymbol) {
        int result = 0;
        for (int i = dump.length - 1; i >= 0; i--) {
            if (dump[i][dump.length - 1 - i] == lastPlacedSymbol) {
                result++;
            }
        }
        return result;
    }

    private int getMainDiagonalFilledCellsCount(char[][] dump, char lastPlacedSymbol) {
        int result = 0;
        for (int i = 0; i < dump.length; i++) {
            if (dump[i][i] == lastPlacedSymbol) {
                result++;
            }
        }
        return result;
    }

    private int getVerticalFilledCellsCount(char[][] dump, Location lastTurn, char lastPlacedSymbol) {
        int result = 0;
        for (int y = 0; y < dump[lastTurn.getX()].length; y++) {
            if (dump[y][lastTurn.getX()] == lastPlacedSymbol) {
                result++;
            }
        }
        return result;
    }

    private int getHorizontalFilledCellsCount(char[][] dump, Location lastTurn, char lastPlacedSymbol) {
        int result = 0;
        for (int x = 0; x < dump[lastTurn.getY()].length; x++) {
            if (dump[lastTurn.getY()][x] == lastPlacedSymbol) {
                result++;
            }
        }
        return result;
    }

    private int getEmptyCellsCount(char[][] dump) {
        int result = 0;
        for (char[] row : dump) {
            for (char cell : row) {
                if (cell == RepositoryConstants.BOARD_EMPTY_CELL) {
                    result++;
                }
            }
        }
        return result;
    }

    private String getStateCodeByLastPlacedSymbol(char lastPlacedSymbol) {
        String result = null;
        if (lastPlacedSymbol == RepositoryConstants.BOARD_X_CELL) {
            result = RepositoryConstants.STATE_CODE_X_WON;
        } else if (lastPlacedSymbol == RepositoryConstants.BOARD_O_CELL) {
            result = RepositoryConstants.STATE_CODE_O_WON;
        }

        return result;
    }
}
