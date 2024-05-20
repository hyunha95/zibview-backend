package com.view.zib.domain.location.entity;

import com.view.zib.domain.location.controller.request.SaveLocationRequest;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Location extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private double accuracy;
    private double altitude;
    private double altitudeAccuracy;
    private double heading;
    private double latitude;
    private double longitude;
    private double speed;
    private double timestamp;


    public static Location of(SaveLocationRequest request, User user) {
        Location location = Location.builder()
                .user(user)
                .accuracy(request.coords().accuracy())
                .altitude(request.coords().altitude())
                .altitudeAccuracy(request.coords().altitudeAccuracy())
                .heading(request.coords().heading())
                .latitude(request.coords().latitude())
                .longitude(request.coords().longitude())
                .speed(request.coords().speed())
                .timestamp(request.timestamp())
                .build();

        location.getUser().addEntity(location);

        return location;
    }

    public void update(SaveLocationRequest request) {
        this.accuracy = request.coords().accuracy();
        this.altitude = request.coords().altitude();
        this.altitudeAccuracy = request.coords().altitudeAccuracy();
        this.heading = request.coords().heading();
        this.latitude = request.coords().latitude();
        this.longitude = request.coords().longitude();
        this.speed = request.coords().speed();
        this.timestamp = request.timestamp();
    }
}
