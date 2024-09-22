package com.view.zib.domain.address.event;

import com.view.zib.domain.client.kako.domain.Coordinate;
import com.view.zib.global.event.AbstractEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoadNameAddressUpdateCoordinateEvent extends AbstractEvent {
    private String managementNo;
    private Coordinate coordinate;

    public RoadNameAddressUpdateCoordinateEvent(String managementNo, Coordinate coordinate, LocalDateTime eventDateTime) {
        super(eventDateTime);
        this.managementNo = managementNo;
        this.coordinate = coordinate;
    }

    public static RoadNameAddressUpdateCoordinateEvent of(String managementNo, Coordinate coordinate, LocalDateTime eventDateTime) {
        return new RoadNameAddressUpdateCoordinateEvent(managementNo, coordinate, eventDateTime);
    }
}
