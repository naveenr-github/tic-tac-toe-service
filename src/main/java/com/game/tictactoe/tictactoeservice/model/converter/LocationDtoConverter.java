package com.game.tictactoe.tictactoeservice.model.converter;

import org.springframework.stereotype.Component;
import com.game.tictactoe.tictactoeservice.model.dto.LocationDto;
import com.game.tictactoe.tictactoeservice.model.Location;

/**
 * 
 * @author NA361613
 *
 */
@Component
public class LocationDtoConverter implements Converter<Location, LocationDto> {

    @Override
    public LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.x = location.getX();
        dto.y = location.getY();
        return dto;
    }

    @Override
    public Location fromDto(LocationDto locationDto) {
        Location entity = new Location();
        entity.setX(locationDto.x);
        entity.setY(locationDto.y);
        return entity;
    }
}
