package com.view.zib.domain.elasticsearch.service.impl;

import com.view.zib.domain.elasticsearch.document.PostDocument;
import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import com.view.zib.domain.elasticsearch.repository.PostElasticSearchRepository;
import com.view.zib.domain.elasticsearch.service.PostElasticSearchService;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.repository.dto.LatestPost;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostElasticSearchServiceImpl implements PostElasticSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final PostElasticSearchRepository postElasticSearchRepository;
    private final PostRepository postRepository;

    @Override
    public Page<PostDocument> multiMatchAddressAndBuildingName(String query, Pageable pageable) {
//        NativeSearchQuery build = new NativeSearchQueryBuilder()
//                .withPageable(pageable)
//                .withQuery(QueryBuilders.multiMatchQuery(query, "address", "buildingName"))
//                .build();
//        return elasticsearchOperations.search(build, PostDocument.class);

        return null;
//        return postElasticSearchRepository.multiMatchAddressAndBuildingName(query, pageable);
    }

    @Override
    public List<PostSearchAsYouType> searchAsYouTypeAddressAndBuildingName(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 10))
                .withQuery(QueryBuilders.multiMatchQuery(query, "address", "address._2gram", "address._3gram", "buildingName", "buildingName._2gram", "buildingName._3gram"))
                .build();

        return elasticsearchOperations.search(nativeSearchQuery, PostSearchAsYouType.class)
                .map(SearchHit::getContent)
                .toList();
    }

    @Override
    public void indexPost() {
        int pageNumber = 0;
        int pageSize = 100000;
        Long postId = null;

        while (true) {
            Slice<LatestPost> allLatestPosts = postRepository.findAllLatestPosts(PageRequest.of(pageNumber, pageSize));
            List<PostSearchAsYouType> content = allLatestPosts.map(LatestPost::toPostSearchAsYouType).getContent();
            elasticsearchOperations.save(content);

            if (!CollectionUtils.isEmpty(content)) {
                postId = content.getLast().getId();
            }

            pageNumber++;
            if (allLatestPosts.isLast()) {
                break;
            }
        }
    }


}