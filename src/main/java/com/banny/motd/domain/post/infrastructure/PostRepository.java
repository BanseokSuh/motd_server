package com.banny.motd.domain.post.infrastructure;

import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post getById(Long id);

    Optional<Post> findById(Long id);

    List<Post> getPostList(SearchRequest request);

    Post save(Post post);

    void delete(Post post);

}
