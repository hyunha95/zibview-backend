package com.view.zib.domain.location.controller.request;

/**
 * {"coords": {"accuracy": 5, "altitude": 0, "altitudeAccuracy": -1, "heading": -1, "latitude": 37.785834, "longitude": -122.406417, "speed": -1}, "timestamp": 1716158866110.6719}
 */
public record SaveLocationRequest(
        Coords coords,
        double timestamp
) {

    public record Coords(
            double accuracy,
            double altitude,
            double altitudeAccuracy,
            double heading,
            double latitude,
            double longitude,
            double speed) {
    }
}
