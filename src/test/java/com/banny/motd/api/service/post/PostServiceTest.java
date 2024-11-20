package com.banny.motd.api.service.post;

import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.api.service.post.request.PostModifyServiceRequest;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@Sql(executionPhase = BEFORE_TEST_CLASS, scripts = {"/sql/schema.sql"})
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"/sql/init/createUsers.sql", "/sql/init/createPosts.sql"})
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"/sql/reset.sql"})
@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("게시글을 등록한다.")
    void createPost() {
        // given
        List<String> imageUrls = List.of("image_url_001", "image_url_002");
        String content = "content_test";
        PostCreateServiceRequest request = PostCreateServiceRequest.builder()
                .imageUrls(imageUrls)
                .content(content)
                .build();
        Long userId = 1L;

        // when
        Post createdPost = postService.createPost(request, userId);

        // then
        assertThat(createdPost)
                .extracting("imageUrls", "content")
                .contains(imageUrls, content);
    }

    @Test
    @DisplayName("게시글 등록 시 존재하지 않는 유저일 경우 예외를 반환한다.")
    void createPostWithNotExistUser() {
        // given
        List<String> imageUrls = List.of("image_url_001", "image_url_002");
        String content = "content_test";
        PostCreateServiceRequest request = PostCreateServiceRequest.builder()
                .imageUrls(imageUrls)
                .content(content)
                .build();
        Long userId = 999L;

        // when // then
        assertThatThrownBy(() -> postService.createPost(request, userId))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc")
                .contains(
                        ApiStatusType.FAIL_USER_NOT_FOUND.getCode(),
                        ApiStatusType.FAIL_USER_NOT_FOUND.getDesc()
                );
    }

    @Test
    @DisplayName("게시글 목록을 조회한다.")
    void getPostList() {
        // given
        SearchRequest request = new SearchRequest();

        // when
        List<Post> posts = postService.getPostList(request);

        // then
        assertThat(posts).hasSize(10)
                .extracting("id", "imageUrls", "content")
                .contains(
                        tuple(10L, List.of("imageUrl010", "imageUrl010-1"), "content10"),
                        tuple(9L, List.of("imageUrl009", "imageUrl009-1"), "content09"),
                        tuple(8L, List.of("imageUrl008", "imageUrl008-1"), "content08"),
                        tuple(7L, List.of("imageUrl007", "imageUrl007-1"), "content07"),
                        tuple(6L, List.of("imageUrl006", "imageUrl006-1"), "content06"),
                        tuple(5L, List.of("imageUrl005", "imageUrl005-1"), "content05"),
                        tuple(4L, List.of("imageUrl004", "imageUrl004-1"), "content04"),
                        tuple(3L, List.of("imageUrl003", "imageUrl003-1"), "content03"),
                        tuple(2L, List.of("imageUrl002", "imageUrl002-1"), "content02"),
                        tuple(1L, List.of("imageUrl001", "imageUrl001-1"), "content01")
                );
    }

    @Test
    @DisplayName("게시글 상세 정보를 조회한다.")
    void getPost() {
        // given
        Long postId = 1L;

        // when
        PostDetail postDetail = postService.getPost(postId);

        // then
        assertThat(postDetail)
                .extracting("id", "imageUrls", "content", "likeList", "likeList")
                .contains(postId, List.of("imageUrl001", "imageUrl001-1"), "content01", List.of(), List.of());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회 시 예외가 발생한다.")
    void getPostWithNotExistPostId() {
        // given
        Long notExistPostId = 999L;

        // when // then
        assertThatThrownBy(() -> postService.getPost(notExistPostId))
                .extracting("status.code", "status.desc")
                .contains(ApiStatusType.FAIL_POST_NOT_FOUND.getCode(), ApiStatusType.FAIL_POST_NOT_FOUND.getDesc());
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void modifyPost() {
        // given
        Long postId = 1L;
        Long userId = 1L;
        String content = "I've modified the content of a post";
        PostModifyServiceRequest request = PostModifyServiceRequest.builder()
                .content(content)
                .build();

        // when
        postService.modifyPost(postId, request, userId);

        // then
        assertThat(postService.getPost(postId))
                .extracting("id", "content")
                .contains(postId, content);
    }

    @Test
    @DisplayName("게시글 수정 시 작성자가 아니면 예외를 반환한다.")
    void modifyPostWithNotAuthor() {
        // given
        Long postId = 1L;
        Long userId = 5L; // is not author
        String content = "I've modified the content of a post";
        PostModifyServiceRequest request = PostModifyServiceRequest.builder()
                .content(content)
                .build();

        // when // then
        assertThatThrownBy(() -> postService.modifyPost(postId, request, userId))
                .extracting("status.code", "status.desc")
                .contains(ApiStatusType.FAIL_INVALID_PERMISSION.getCode(), ApiStatusType.FAIL_INVALID_PERMISSION.getDesc());
    }

    @Test
    @DisplayName("게시글을 삭제한다.")
    void deletePost() {
        // given
        Long postId = 1L;
        Long userId = 1L;

        // when
        postService.deletePost(postId, userId);

        // then
        assertThatThrownBy(() -> postService.getPost(postId))
                .extracting("status.code", "status.desc")
                .contains(ApiStatusType.FAIL_POST_NOT_FOUND.getCode(), ApiStatusType.FAIL_POST_NOT_FOUND.getDesc());
    }

    @Test
    @DisplayName("게시글 삭제 시 게시글이 존재하지 않으면 예외를 반환한다.")
    void deletePostWithNotExistPost() {
        // given
        Long postId = 999L;
        Long userId = 1L;

        // when // then
        assertThatThrownBy(() -> postService.deletePost(postId, userId))
                .extracting("status.code", "status.desc")
                .contains(ApiStatusType.FAIL_POST_NOT_FOUND.getCode(), ApiStatusType.FAIL_POST_NOT_FOUND.getDesc());
    }

}