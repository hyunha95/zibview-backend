package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.post.service.PostCommandService;
import com.view.zib.domain.user.entity.UserAddress;
import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.domain.user.entity.UserPost;
import com.view.zib.domain.user.repository.UserAddressRepository;
import com.view.zib.domain.user.repository.UserPostRepository;
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
    private final UserPostRepository userPostRepository;
    private final SubPostRepository subPostRepository;

    @Override
    public Long save(PostRequest.Save request, Document kakaoMapResponse) {
        // user
        UserEntity user = userService.getBySubject(authService.getSubject());

        // images
        List<Image> images = imageRepository.findByUuidIn(request.getImageUuids());

        // address
        Address address = Address.of(request.getAddress(), images, kakaoMapResponse);
        addressRepository.save(address);

        // userAddress
        UserAddress userAddress = UserAddress.of(user, address, request.getResidencyStartDate(), request.getResidencyEndDate());
        userAddressRepository.save(userAddress);

        // post
        Post post = postRepository.findByAddressAndAddressType(address.getAddress(), address.getAddressType())
                        .orElseGet(() -> {
                            Post newPost = Post.from(address);
                            postRepository.save(newPost);
                            return newPost;
                        });

        // subPost
        SubPost subPost = SubPost.of(user, request, images, post);
        subPostRepository.save(subPost);

        // userPost
        UserPost userPost = UserPost.of(user, post);
        userPostRepository.save(userPost);

        return post.getId();
    }
}
