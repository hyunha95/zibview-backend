package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.service.PostCommandService;
import com.view.zib.domain.user.entity.UserAddress;
import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.domain.user.repository.UserAddressRepository;
import com.view.zib.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Builder
@RequiredArgsConstructor
@Service
public class PostCommandServiceImpl implements PostCommandService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserAddressRepository userAddressRepository;
    private final AuthService authService;
    private final AddressRepository addressRepository;

    @Override
    public Long save(PostRequest.Save request, Document kakaoMapResponse) {
        // user
        UserEntity user = userService.getById(authService.getUserId());

        // images
        List<Image> images = imageRepository.findByUuidIn(request.getImageUuids());

        // address
        Address address = Address.from(request.getAddress(), images, kakaoMapResponse);
        addressRepository.save(address);

        // userAddress
        UserAddress userAddress = UserAddress.from(user, address, request.getResidencyStartDate(), request.getResidencyEndDate());
        userAddressRepository.save(userAddress);

        // post
        Post post = Post.from(authService.getUser(), request, images, address);
        postRepository.save(post);

        return post.getId();
    }
}
