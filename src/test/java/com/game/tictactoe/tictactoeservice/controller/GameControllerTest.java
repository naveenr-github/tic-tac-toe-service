package com.game.tictactoe.tictactoeservice.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.model.Snapshot;
import com.game.tictactoe.tictactoeservice.model.State;
import com.game.tictactoe.tictactoeservice.model.converter.GameBriefDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.GameFullDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.LocationDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.StateDtoConverter;
import com.game.tictactoe.tictactoeservice.model.dto.GameBriefDto;
import com.game.tictactoe.tictactoeservice.model.dto.GameFullDto;
import com.game.tictactoe.tictactoeservice.model.dto.LocationDto;
import com.game.tictactoe.tictactoeservice.model.dto.StateDto;
import com.game.tictactoe.tictactoeservice.repository.GameRepository;
import com.game.tictactoe.tictactoeservice.repository.LocationRepository;
import com.game.tictactoe.tictactoeservice.repository.SnapshotRepository;
import com.game.tictactoe.tictactoeservice.repository.StateRepository;
import com.game.tictactoe.tictactoeservice.service.GameService;
import com.game.tictactoe.tictactoeservice.service.SnapshotService;
import com.game.tictactoe.tictactoeservice.service.impl.GameServiceImpl;
import com.game.tictactoe.tictactoeservice.service.impl.SnapshotServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;



@RunWith(SpringJUnit4ClassRunner.class)
public class GameControllerTest {
	
	GameController gameController;
	@Mock
	private GameRepository gameRepository;
	@Mock
    private StateRepository stateRepository;
	
	@Mock
    private LocationRepository locationRepository;
	@Mock
    private SnapshotRepository snapshotRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		SnapshotService snapshotService =  new SnapshotServiceImpl();
		GameService gameService = new GameServiceImpl(gameRepository, stateRepository, snapshotService, locationRepository, snapshotRepository);
		GameBriefDtoConverter briefDtoConverter = new GameBriefDtoConverter();
		StateDtoConverter stateDtoConverter = new StateDtoConverter();
		LocationDtoConverter locationDtoConverter = new LocationDtoConverter();
		GameFullDtoConverter fullDtoConverter = new GameFullDtoConverter(stateDtoConverter, locationDtoConverter);		
		gameController = new GameController(gameService, briefDtoConverter, fullDtoConverter, locationDtoConverter, stateDtoConverter);
		
	}
	
	/**
	 * Test case to verify create Game successfully.
	 */
	@org.junit.Test
	public void testCreateGame() {
		
		GameBriefDto gameBriefDto = new GameBriefDto();
		gameBriefDto.name = "naveen";
		gameBriefDto.character = 'o';
		
		State initialState = new State();
		initialState.setId(1l);
		initialState.setCode("IN_PROGRESS");
		
		Mockito.doReturn(initialState).when(stateRepository).getInitialState();
		Mockito.doReturn(11l).when(snapshotRepository).add(Mockito.any());
		Mockito.doReturn(100l).when(gameRepository).add(Mockito.any());
		
		ResponseEntity<GameBriefDto> result = gameController.addGame(gameBriefDto);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	
	/**
	 * Test Case to retrieve the Game Details based on Game Id successfully.
	 */	
	@org.junit.Test
	public void testFindGame() {
		Game game = getGame();		
		Mockito.doReturn(game).when(gameRepository).findById(Mockito.any());		
		ResponseEntity<GameFullDto> result = gameController.findGameById(100l);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	/**
	 * Test Case to verify when game id is not available.
	 */
	@org.junit.Test
	public void testFindGameIsNull() {
		Mockito.doReturn(null).when(gameRepository).findById(Mockito.any());
		
		ResponseEntity<GameFullDto> result = gameController.findGameById(100l);
		assertEquals(404, result.getStatusCodeValue());
	}
	
	/**
	 * Test case to move the game to the location.
	 */
	@org.junit.Test
	public void testMoveGame() {
		
		char[][] dump = new char[3][3];
		dump[1][0] = ' ';
		
		Game game = getGame();
		game.getSnapshot().setDump(dump);
		
		Mockito.doReturn(game).when(gameRepository).findById(Mockito.any());
		Mockito.doReturn(1l).when(locationRepository).add(Mockito.any());
		Mockito.doReturn(getState()).when(stateRepository).findByCode(Mockito.any());
		
		Mockito.doNothing().when(gameRepository).addTurn(Mockito.any(), Mockito.any());
		Mockito.doNothing().when(snapshotRepository).update(Mockito.any());
		Mockito.doNothing().when(gameRepository).update(Mockito.any());
		ResponseEntity<StateDto> result = gameController.makeMove(100l,getLocationDto());
		assertEquals(200, result.getStatusCodeValue());
	}
	
	/**
	 * Test Case to verify when position is already filled in the given location.
	 */
	@org.junit.Test
	public void testMoveGameInSameLocation() {
		Game game = getGame();
		Mockito.doReturn(game).when(gameRepository).findById(Mockito.any());
		Mockito.doReturn(1l).when(locationRepository).add(Mockito.any());
		Mockito.doReturn(getState()).when(stateRepository).findByCode(Mockito.any());
		
		Mockito.doNothing().when(gameRepository).addTurn(Mockito.any(), Mockito.any());
		Mockito.doNothing().when(snapshotRepository).update(Mockito.any());
		Mockito.doNothing().when(gameRepository).update(Mockito.any());
		ResponseEntity<StateDto> result = gameController.makeMove(100l,getLocationDto());
		assertEquals(200, result.getStatusCodeValue());
		assertEquals("Position Already Occupied", result.getBody().title);
		assertEquals("IN_PROGRESS", result.getBody().code);
		System.out.println(" state code"+ result.getBody().title);
		System.out.println(" state code"+ result.getBody().code);
	}
	
	private Game getGame() {
		char[][] dump = new char[3][3];
		dump[1][0] = 'x';
		
		Location location = new Location();
		location.setX(0);
		location.setY(1);
		
		Snapshot snapshot = new Snapshot();
		snapshot.setDump(dump);
		snapshot.setId(1l);
		snapshot.setLastTurn(location);
		
		Game game = new Game();
		game.setName("Naveen");
		game.setId(100l);
		game.setCharacter('x');
		game.setSnapshot(snapshot);
		
		game.setState(getState());
		return game;
	}
	
	private LocationDto getLocationDto() {
		LocationDto locationDto = new LocationDto();
		locationDto.x = 0;
		locationDto.y = 1;
		return locationDto;
	}
	
	private State getState() {
		State state = new State();
		state.setId(1l);
		state.setCode("IN_PROGRESS");
		return state;
	}
}
