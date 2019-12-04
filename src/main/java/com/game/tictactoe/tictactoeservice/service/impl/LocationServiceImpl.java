package com.game.tictactoe.tictactoeservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.tictactoe.tictactoeservice.model.Location;
import com.game.tictactoe.tictactoeservice.repository.LocationRepository;
import com.game.tictactoe.tictactoeservice.service.LocationService;

import java.util.List;

/**
 * 
 * @author NA361613
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getTurnHistory(Long gameId) {
        return locationRepository.getTurnsByGameId(gameId);
    }
}
