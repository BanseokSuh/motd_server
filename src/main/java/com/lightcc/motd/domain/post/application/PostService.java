package com.lightcc.motd.domain.post.application;

import com.lightcc.motd.domain.post.domain.Post;

public interface PostService {

    Post createPost(String title, String content, Long userId);

    Post getPost(Long postId);

    void modifyPost(Long postId, String title, String content, Long userId);

    void deletePost(Long postId, Long userId);
}
