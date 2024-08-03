package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.domain.PostAuthor;
import com.banny.motd.domain.post.domain.PostAuthorComment;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.domain.post.domain.Post;

import java.util.List;

public interface PostService {

    Post createPost(String title, String content, Long userId);

    List<PostAuthor> getPostList(SearchRequest request);

    PostAuthorComment getPost(Long postId);

    void modifyPost(Long postId, String title, String content, Long userId);

    void deletePost(Long postId, Long userId);
}
