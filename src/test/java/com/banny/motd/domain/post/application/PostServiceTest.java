package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.application.repository.PostRepository;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEventsTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("게시글_생성시_작성유저가_존재하지_않을_경우")
    void post_create_user_not_found() {
        // given
        String title = "title";
        String content = "content";
        Long userId = 1L;

        // mock
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when, then
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.createPost(title, content, userId));
        assertEquals(ResultType.USER_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_단건_조회")
    void post_get() {
        // given
        Long postId = 1L;

        // mock
        when(postRepository.findById(postId)).thenReturn(Optional.of(mock(PostEntity.class)));

        // when, then
        assertDoesNotThrow(() -> postService.getPost(postId));
    }

    @Test
    @DisplayName("게시글_단건_조회시_게시글이_존재하지_않을_경우")
    void post_get_not_found() {
        // given
        Long postId = 1L;

        // mock
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when, then
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.getPost(postId));
        assertEquals(ResultType.POST_NOT_FOUND.getCode(), e.getResult().getCode());
    }

}