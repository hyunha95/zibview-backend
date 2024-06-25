package com.view.zib.domain.post.service;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.SubPostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.user.entity.User;

import java.util.List;

public interface SubPostCommandService {
    Long create(SubPostRequest.Save request, Post post, List<Image> images, User currentUser);
}
