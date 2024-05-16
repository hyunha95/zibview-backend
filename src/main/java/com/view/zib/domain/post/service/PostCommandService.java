package com.view.zib.domain.post.service;

import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.post.controller.request.PostRequest;

public interface PostCommandService {

    Long save(PostRequest.Save request, Document kakaoMapResponse);
}
