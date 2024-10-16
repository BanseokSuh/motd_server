package com.banny.motd.api.service.post;

import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;

public interface PostService {

    Post createPost(List<String> imageUrls, String content, Long userId);

    List<PostList> getPostList(SearchRequest request);

    PostDetail getPost(Long postId);

    void modifyPost(Long postId, String content, Long userId);

    void deletePost(Long postId, Long userId);

}
