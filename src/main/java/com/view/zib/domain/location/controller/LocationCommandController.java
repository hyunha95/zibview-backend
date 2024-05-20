package com.view.zib.domain.location.controller;

import com.view.zib.domain.location.controller.request.SaveLocationRequest;
import com.view.zib.domain.location.service.LocationCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/locations")
@RestController
public class LocationCommandController {

    private final LocationCommandService locationCommandService;

    @PostMapping
    public ResponseEntity<Void> saveLocation(@RequestBody SaveLocationRequest request) {
        locationCommandService.saveLocation(request);
        return ResponseEntity.noContent().build();
    }
}
