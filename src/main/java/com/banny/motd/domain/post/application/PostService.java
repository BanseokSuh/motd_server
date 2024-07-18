package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post createPost(String title, String content, Long userId);

    Page<Post> getPostList(Pageable pageable);

    Post getPost(Long postId);

    void modifyPost(Long postId, String title, String content, Long userId);

    void deletePost(Long postId, Long userId);
}
