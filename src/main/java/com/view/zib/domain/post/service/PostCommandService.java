package com.view.zib.domain.post.service;

import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.post.controller.request.PostRequest;

public interface PostCommandService {

    Long save(PostRequest.Save request, KakaoAddressResponse kakaoAddressResponse);
}
