package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.application.repository.PostRepository;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글_생성")
    void post_create() {
        // given
        String title = "title";
        String content = "content";
        Long userId = 1L;

        // mock
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        // when, then
        assertDoesNotThrow(() -> postService.createPost(title, content, userId));
    }

}