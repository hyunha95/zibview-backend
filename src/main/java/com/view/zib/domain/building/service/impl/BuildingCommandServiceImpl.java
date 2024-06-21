package com.view.zib.domain.building.service.impl;

import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.building.entity.BuildingInfo;
import com.view.zib.domain.building.repository.BuildingRepository;
import com.view.zib.domain.building.service.BuildingCommandService;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BuildingCommandServiceImpl implements BuildingCommandService {

    private final BuildingRepository buildingRepository;

    @Transactional
    @Override
    public void updateCoordinate(Post post, Coordinate coordinate) {
        BuildingInfo buildingInfo = getById(null); // post.getBuildingInfo().getId()
        buildingInfo.updateCoordinate(coordinate);
    }

    public BuildingInfo getById(Long buildingId) {
        return this.findById(buildingId).orElseThrow(() -> new ResourceNotFoundException("Building", buildingId));
    }

    public Optional<BuildingInfo> findById(Long buildingId) {
        return buildingRepository.findById(buildingId);
    }
}
