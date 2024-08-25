package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.application.repository.PostRepository;
import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
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
        UserEntity userEntity = mock(UserEntity.class);
        User author = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntity.toDomain()).thenReturn(author);
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
        assertEquals(ResultType.FAIL_USER_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_단건_조회")
    void post_get() {
        // given
        Long postId = 6L;
        Long userId = 1L;

        // mock
        PostEntity postEntity = mock(PostEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        Post post = mock(Post.class);
        User author = mock(User.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntity.toDomain()).thenReturn(post);
        when(post.getAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntity.toDomain()).thenReturn(author);

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
        assertEquals(ResultType.FAIL_POST_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    // TODO: Is it right?
    @Test
    @DisplayName("게시글_수정")
    void post_modify() {
        // given
        Long postId = 1L;
        Long userId = 1L;

        // mock
        PostEntity postEntity = mock(PostEntity.class);
        Post post = mock(Post.class);
        User author = mock(User.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntity.toDomain()).thenReturn(post);
        when(post.getAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.saveAndFlush(postEntity)).thenReturn(postEntity);
    }
}