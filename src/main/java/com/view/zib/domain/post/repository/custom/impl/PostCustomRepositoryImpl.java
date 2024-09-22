package com.view.zib.domain.post.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.view.zib.domain.post.repository.custom.PostCustomRepository;
import com.view.zib.domain.post.repository.dto.LatestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.view.zib.domain.address.entity.QAddressAdditionalInfo.addressAdditionalInfo;
import static com.view.zib.domain.address.entity.QRoadNameAddress.roadNameAddress;
import static com.view.zib.domain.address.entity.QRoadNameCode.roadNameCode;
import static com.view.zib.domain.post.entity.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<LatestPost> findAllLatestPosts(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        List<LatestPost> responses = jpaQueryFactory.select(
                        Projections.fields(LatestPost.class,
                                post.id.as("postId"),
                                roadNameCode.sggName,
                                roadNameCode.emdName,
                                addressAdditionalInfo.buildingName,
                                addressAdditionalInfo.sggBuildingName,
                                post.likeCount,
                                post.commentCount,
                                post.viewCount,
                                post.updatedAt
                        )
                )
                .from(post)
                .join(post.roadNameAddress, roadNameAddress)
                .join(roadNameAddress.roadNameCode, roadNameCode)
                .join(roadNameAddress.addressAdditionalInfo, addressAdditionalInfo)
                .where(addressAdditionalInfo.apartmentYn.eq("1"))
                .orderBy(post.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1L) // // limit보다 데이터를 1개 더 들고와서, 해당 데이터가 있다면 hasNext 변수에 true를 넣어 알림
                .fetch();

        boolean hasNext = false;
        if (responses.size() > pageable.getPageSize()) {
            responses.remove(pageSize); // 위에서 + 1 해서 들고온 데이터는 hasNext 확인 후 삭제
            hasNext = true;
        }

        return new SliceImpl<>(responses, pageable, hasNext);
    }
}
