package com.banny.motd.domain.post.infrastructure;

import com.banny.motd.domain.post.api.dto.request.PostSearchRequest;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostEntity> getPostList(PostSearchRequest request);
}
