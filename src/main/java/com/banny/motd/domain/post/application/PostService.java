package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.api.dto.request.PostSearchRequest;
import com.banny.motd.domain.post.domain.Post;

import java.util.List;

public interface PostService {

    Post createPost(String title, String content, Long userId);

    List<Post> getPostList(PostSearchRequest request);

    Post getPost(Long postId);

    void modifyPost(Long postId, String title, String content, Long userId);

    void deletePost(Long postId, Long userId);
}
