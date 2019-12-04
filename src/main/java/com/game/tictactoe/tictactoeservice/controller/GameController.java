package com.game.tictactoe.tictactoeservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game.tictactoe.tictactoeservice.model.converter.GameBriefDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.GameFullDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.LocationDtoConverter;
import com.game.tictactoe.tictactoeservice.model.converter.StateDtoConverter;
import com.game.tictactoe.tictactoeservice.model.dto.GameBriefDto;
import com.game.tictactoe.tictactoeservice.model.dto.GameFullDto;
import com.game.tictactoe.tictactoeservice.model.dto.LocationDto;
import com.game.tictactoe.tictactoeservice.model.dto.StateDto;
import com.game.tictactoe.tictactoeservice.model.Game;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.model.State;
import com.game.tictactoe.tictactoeservice.service.GameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author NA361613
 * Game Controller class which exposes rest api to play the game.
 * 
 */
@RestController
@RequestMapping("/game")
@Api(value = " Tic Tac Toe Game Rest API")
public class GameController {

    private GameService gameService;
    private GameBriefDtoConverter briefDtoConverter;
    private GameFullDtoConverter fullDtoConverter;
    private LocationDtoConverter locationDtoConverter;
    private StateDtoConverter stateDtoConverter;



    @Autowired
    public GameController(
        GameService gameService,
        GameBriefDtoConverter briefDtoConverter,
        GameFullDtoConverter fullDtoConverter,
        LocationDtoConverter locationDtoConverter,
        StateDtoConverter stateDtoConverter) {
        this.gameService = gameService;

        this.briefDtoConverter = briefDtoConverter;
        this.fullDtoConverter = fullDtoConverter;
        this.locationDtoConverter = locationDtoConverter;
        this.stateDtoConverter = stateDtoConverter;
    }

    

    @ApiOperation(value =" Retrieve Game Details by Game Id", response = GameFullDto.class)
    @ApiResponses(value = {
    		@ApiResponse(code =200, message = "Retrieved Game Successfully"),
    		@ApiResponse(code = 403, message = " Resource you are trying to reach is forbidden"),
    		@ApiResponse(code = 404, message = " Game is not available for given game id")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameFullDto> findGameById(@PathVariable Long id) {
    	
        Game game = gameService.findById(id);
        if(null != game) {
        	GameFullDto result = fullDtoConverter.toDto(game);
            return ResponseEntity.ok(result);
        }
        
        return new ResponseEntity<GameFullDto>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value =" Create Game ", response = GameBriefDto.class)
    @ApiResponses(value = {
    		@ApiResponse(code =200, message = "Game Created Successfully"),
    		@ApiResponse(code = 403, message = " Resource you are trying to reach is forbidden"),
    		@ApiResponse(code = 404, message = "Resource is not found")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBriefDto> addGame(@RequestBody GameBriefDto dto) {
        Game game  = gameService.addGame(dto.name, dto.character);
        GameBriefDto result = briefDtoConverter.toDto(game);
        return ResponseEntity.ok(result);
    }
    
    @ApiOperation(value =" Play the game with  Game Id by giving postion x and y", response = StateDto.class)
    @ApiResponses(value = {
    		@ApiResponse(code =200, message = "Updated the Move successfully"),
    		@ApiResponse(code = 403, message = " Resource you are trying to reach is forbidden"),
    		@ApiResponse(code = 404, message = "Game is not available for given game id")
    })
    @PostMapping(value = "/{id}/move", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StateDto> makeMove(@PathVariable Long id, @RequestBody LocationDto locationDto) {
        Location location = locationDtoConverter.fromDto(locationDto);
        State state = gameService.makeMove(id, location);
        StateDto result = stateDtoConverter.toDto(state);
        return ResponseEntity.ok(result);
    }

    
}
