package com.banny.motd.api.service.post;

import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.exception.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void init() {
        User user = User.builder()
                .id(1L)
                .loginId("test000")
                .userName("서반석")
                .nickName("반석")
                .email("still3028@gmail.com")
                .password("password!")
                .gender(Gender.from("MALE"))
                .userRole(UserRole.USER)
                .userStatus(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("게시글이 정상적으로 등록된다.")
    @Test
    void createPost() {
        // given
        List<String> imageUrls = List.of("image1", "image2", "image3");
        String content = "content";
        PostCreateServiceRequest request = PostCreateServiceRequest.builder()
                .imageUrls(imageUrls)
                .content(content)
                .build();
        Long userId = 1L;

        // when
        Post createdPost = postService.createPost(request, userId);

        // then
        assertThat(createdPost)
                .extracting("id", "imageUrls", "content", "author.id")
                .contains(1L, imageUrls, content, userId);
    }

    @DisplayName("게시글 생성 시 유저가 존재하지 않으면 예외가 발생한다.")
    @Test
    void createPostWithNotExistUser() {
        // given
        List<String> imageUrls = List.of("image1", "image2", "image3");
        String content = "content";
        PostCreateServiceRequest request = PostCreateServiceRequest.builder()
                .imageUrls(imageUrls)
                .content(content)
                .build();
        Long userId = 2L;

        // when // then
        assertThatThrownBy(() -> postService.createPost(request, userId))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_USER_NOT_FOUND.getCode(),
                        ApiResponseStatusType.FAIL_USER_NOT_FOUND.getDesc(),
                        String.format("User %d is not found", userId)
                );
    }

    @DisplayName("게시글 목록이 정상적으로 조회된다.")
    @Test
    void getPostList() {
        // given
        createPosts(15);
        SearchRequest request = SearchRequest.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<Post> postList = postService.getPostList(request);

        // then
        assertThat(postList).hasSize(10)
                .extracting("id")
                .containsExactly(15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L)
                .doesNotContain(5L, 4L, 3L, 2L, 1L);
    }

    @DisplayName("게시글이 정상적으로 조회된다.")
    @Test
    void getPost() {
        // given
        createPosts(1);

        // when
        PostDetail post = postService.getPost(1L);

        // then
        assertThat(post)
                .extracting("id", "content", "author.id", "likeList", "commentList")
                .contains(1L, "content1", 1L, List.of(), List.of());
    }

    private void createPosts(int count) {
        for (int i = 1; i <= count; i++) {
            Post post = Post.builder()
                    .author(User.builder().id(1L).build())
                    .content("content" + i)
                    .imageUrls(List.of("image" + i))
                    .build();
            postRepository.save(post);
        }
    }

}