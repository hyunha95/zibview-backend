package com.view.zib.domain.building.service;

import com.view.zib.domain.client.kako.domain.Coordinate;
import com.view.zib.domain.post.entity.Post;

public interface BuildingCommandService {
    void updateCoordinate(Post post, Coordinate coordinate);
}
