package com.view.zib.domain.location.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.location.controller.request.SaveLocationRequest;
import com.view.zib.domain.location.entity.Location;
import com.view.zib.domain.location.service.LocationCommandService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.repository.LocationRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Builder
@Transactional
@RequiredArgsConstructor
@Service
public class LocationCommandServiceImpl implements LocationCommandService {

    private final AuthService authService;
    private final LocationRepository locationRepository;

    @Override
    public void saveLocation(SaveLocationRequest request) {
        User currentUser = authService.getCurrentUser();

        locationRepository.findByUser_Id(currentUser.getId())
                .ifPresentOrElse(
                        updateLocation(request),
                        createLocation(request, currentUser)
                );
    }

    private Consumer<Location> updateLocation(SaveLocationRequest request) {
        return location -> location.update(request);
    }

    private Runnable createLocation(SaveLocationRequest request, User currentUser) {
        return () -> locationRepository.save(Location.of(request, currentUser));
    }
}
