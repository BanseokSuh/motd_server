package com.banny.motd.api.service.post;

import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.api.service.post.request.PostModifyServiceRequest;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;

public interface PostService {

    Post createPost(PostCreateServiceRequest request, Long userId);

    List<PostList> getPostList(SearchRequest request);

    PostDetail getPost(Long postId);

    void modifyPost(Long postId, PostModifyServiceRequest request, Long userId);

    void deletePost(Long postId, Long userId);

}
