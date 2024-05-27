package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.entity.ContractInfo;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.ContractInfoRepository;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.post.service.PostCommandService;
import com.view.zib.domain.user.entity.UserAddress;
import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.entity.UserPost;
import com.view.zib.domain.user.repository.UserAddressRepository;
import com.view.zib.domain.user.repository.UserPostRepository;
import com.view.zib.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Transactional
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
    private final ContractInfoRepository contractInfoRepository;

    @Builder
    public PostCommandServiceImpl(UserService userService, PostRepository postRepository, ImageRepository imageRepository, UserAddressRepository userAddressRepository, AuthService authService, AddressRepository addressRepository, UserPostRepository userPostRepository, SubPostRepository subPostRepository, ContractInfoRepository contractInfoRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userAddressRepository = userAddressRepository;
        this.authService = authService;
        this.addressRepository = addressRepository;
        this.userPostRepository = userPostRepository;
        this.subPostRepository = subPostRepository;
        this.contractInfoRepository = contractInfoRepository;
    }

    @Override
    public Long save(PostRequest.Save request, Document kakaoMapResponse) {
        // user
        User user = userService.getByEmail(authService.getEmail());

        // images
        List<Image> images = imageRepository.findByUuidIn(request.getImageUuids());

        // address
        Address address = Address.of(request.getAddress(), images, kakaoMapResponse);
        addressRepository.save(address);

        // userAddress
        UserAddress userAddress = UserAddress.of(user, address, request.getContractInfo());
        userAddressRepository.save(userAddress);

        // post
        Post post = postRepository
                .findByAddressAndAddressType(address.getAddress(), address.getAddressType())
                .orElseGet(newPost(address));

        // contractInfo
        ContractInfo contractInfo = ContractInfo.of(request.getContractInfo(), post);
        contractInfoRepository.save(contractInfo);

        // subPost
        SubPost subPost = SubPost.of(user, request, images, post, contractInfo);
        subPostRepository.save(subPost);

        // userPost
        UserPost userPost = UserPost.of(user, post);
        userPostRepository.save(userPost);

        post.updateBuildingType(request.getBuildingType());
        post.updateContractInfo(contractInfo);
        post.updateImage(
                Image.getLatestRepresentativeImage(images)
                        .orElse(imageRepository.findLatestRepresentativeImage(post.getId()))
        );

        return post.getId();
    }

    private Supplier<Post> newPost(Address address) {
        return () -> {
            Post newPost = Post.from(address);
            postRepository.save(newPost);
            return newPost;
        };
    }
}
