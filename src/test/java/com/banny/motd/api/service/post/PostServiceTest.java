package com.banny.motd.api.service.post;

import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@Sql(scripts = {"/sql/schema.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {
        "/sql/init/createUsers.sql",
        "/sql/init/createPosts.sql"
}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/reset/reset.sql"}, executionPhase = AFTER_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("게시글이 정상적으로 등록된다.")
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
    void test() {
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


}