package com.banny.motd.domain.post.infrastructure;

import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostEntity> getPostList(SearchRequest request);
}
