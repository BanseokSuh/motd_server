package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.post.domain.PostDetail;
import com.banny.motd.domain.post.domain.PostList;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;

public interface PostService {

    Post createPost(String title, String content, Long userId);

    List<PostList> getPostList(SearchRequest request);

    PostDetail getPost(Long postId);

    void modifyPost(Long postId, String title, String content, Long userId);

    void deletePost(Long postId, Long userId);

}
