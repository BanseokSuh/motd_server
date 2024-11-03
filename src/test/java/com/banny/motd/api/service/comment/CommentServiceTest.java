package com.banny.motd.api.service.comment;

import com.banny.motd.api.service.alarm.producer.AlarmProducer;
import com.banny.motd.api.service.comment.request.CommentCreateServiceRequest;
import com.banny.motd.domain.alarm.AlarmEvent;
import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.comment.infrastructure.CommentRepository;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.enums.TargetType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @MockBean
    private AlarmProducer alarmProducer;

    @BeforeEach
    void setUp() {
        User user = createTestUser();
        createTestPost(user);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("게시글에 댓글을 생성한다.")
    @Test
    void createPostComment() {
        // given
        Long userId = 1L;
        Long postId = 1L;
        CommentCreateServiceRequest request = CommentCreateServiceRequest.builder()
                .comment("comment")
                .build();

        // mock
        willDoNothing().given(alarmProducer).send(any(AlarmEvent.class));

        // when
        commentService.createPostComment(postId, userId, request);

        // then
        assertThat(commentService.getCommentListByPostId(postId)).hasSize(1);
        List<Comment> comments = commentService.getCommentListByPostId(postId);
        assertThat(comments).hasSize(1)
                .extracting("author.id", "targetType", "targetId", "comment")
                .containsExactlyInAnyOrder(
                        tuple(userId, TargetType.POST, postId, "comment")
                );
    }

    private User createTestUser() {
        User user = User.builder()
                .id(1L)
                .loginId("test000")
                .userName("서반석")
                .nickName("반석")
                .email("still3028@gmail.com")
                .password("password!")
                .userRole(UserRole.USER)
                .userStatus(UserStatus.ACTIVE)
                .profileImageUrl("profileImageUrl")
                .gender(Gender.from("MALE"))
                .build();
        return userRepository.save(user);
    }

    private Post createTestPost(User user) {
        Post post = Post.builder()
                .id(1L)
                .imageUrls(List.of("image1", "image2"))
                .content("content")
                .author(user)
                .build();
        return postRepository.save(post);
    }
}