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
import static org.mockito.Mockito.*;

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
        String content = "content";
        Long userId = 1L;

        // mock
        UserEntity userEntity = mock(UserEntity.class);
        User author = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntity.toDomain()).thenReturn(author);
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        // when, then
        assertDoesNotThrow(() -> postService.createPost(content, userId));
    }

    @Test
    @DisplayName("게시글_생성시_작성유저가_존재하지_않을_경우_에러를_반환한다")
    void post_create_user_not_found() {
        // given
        String content = "content";
        Long userId = 1L;

        // mock
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.createPost(content, userId));

        // then
        assertEquals(ResultType.FAIL_USER_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_단건_조회")
    void post_get() {
        // given
        User user = User.builder().id(1L).build();
        Post post = Post.builder()
                .id(1L)
                .content("content")
                .author(User.builder().id(user.getId()).build())
                .build();

        // mock
        PostEntity mockPostEntity = mock(PostEntity.class);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(mockPostEntity));
        when(mockPostEntity.toDomain()).thenReturn(post);

        UserEntity mockUserEntity = mock(UserEntity.class);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(mockUserEntity));
        when(mockUserEntity.toDomain()).thenReturn(user);

        // when, then
        assertDoesNotThrow(() -> postService.getPost(post.getId()));
    }

    @Test
    @DisplayName("게시글_단건_조회시_게시글이_존재하지_않을_경우_에러를_반환한다")
    void post_get_not_found() {
        // given
        Long postId = 1L;

        // mock
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.getPost(postId));

        // then
        assertEquals(ResultType.FAIL_POST_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_수정")
    void post_modify() {
        // given
        User user = User.builder().id(1L).build();
        Post post = Post.builder()
                .author(User.builder().id(user.getId()).build())
                .content("content")
                .build();
        String contentModify = "content-modify";

        // mock
        UserEntity mockUserEntity = mock(UserEntity.class);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(mockUserEntity));
        when(mockUserEntity.toDomain()).thenReturn(user);

        PostEntity mockPostEntity = mock(PostEntity.class);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(mockPostEntity));
        when(mockPostEntity.toDomain()).thenReturn(post);

        // when, then
        assertDoesNotThrow(() -> postService.modifyPost(post.getId(), contentModify, user.getId()));
    }

    @Test
    @DisplayName("게시글_수정시_작성유저가_존재하지_않을_경우_에러를_반환한다")
    void post_modify_user_not_found() {
        // given
        Long userId = 1L;
        Post post = Post.builder()
                .author(User.builder().id(2L).build())
                .content("content")
                .build();
        String content = "content-modify";

        // mock
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.modifyPost(post.getId(), content, userId));

        // then
        assertEquals(ResultType.FAIL_USER_NOT_FOUND.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_수정시_작성유저가_작성자가_아닐_경우_에러를_반환한다")
    void post_modify_not_author() {
        // given
        User user = User.builder().id(1L).build();
        Post post = Post.builder()
                .author(User.builder().id(2L).build())
                .content("content")
                .build();
        String content = "content-modify";

        // mock
        UserEntity mockUserEntity = mock(UserEntity.class);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(mockUserEntity));
        when(mockUserEntity.toDomain()).thenReturn(user);

        PostEntity mockPostEntity = mock(PostEntity.class);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(mockPostEntity));
        when(mockPostEntity.toDomain()).thenReturn(post);

        // when
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.modifyPost(post.getId(), content, user.getId()));

        // then
        assertEquals(ResultType.FAIL_INVALID_PERMISSION.getCode(), e.getResult().getCode());
    }

    @Test
    @DisplayName("게시글_수정시_게시글이_존재하지_않을_경우_애러를_반환한다")
    void post_modify_post_not_found() {
        // given
        Long userId = 1L;
        Long postId = 1L;

        // mock
        UserEntity mockUserEntity = mock(UserEntity.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(mockUserEntity.toDomain()).thenReturn(mock(User.class));

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when
        String content = "content-modify";
        ApplicationException e = assertThrows(ApplicationException.class, () -> postService.modifyPost(postId, content, userId));

        // then
        assertEquals(ResultType.FAIL_POST_NOT_FOUND.getCode(), e.getResult().getCode());
    }
}