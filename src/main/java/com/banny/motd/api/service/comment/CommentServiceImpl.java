package com.banny.motd.api.service.comment;

import com.banny.motd.api.service.alarm.producer.AlarmProducer;
import com.banny.motd.api.service.comment.request.CommentCreateServiceRequest;
import com.banny.motd.domain.alarm.AlarmArgs;
import com.banny.motd.domain.alarm.AlarmType;
import com.banny.motd.domain.alarm.AlarmEvent;
import com.banny.motd.domain.comment.infrastructure.CommentRepository;
import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import com.banny.motd.global.enums.TargetType;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.dto.response.ApiResponseStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmProducer alarmProducer;

    @Override
    @Transactional
    public void commentPost(Long postId, Long userId, CommentCreateServiceRequest request) {
        User user = userRepository.getById(userId);
        Post post = getPostByIdOrException(postId);

        commentRepository.save(CommentEntity.of(UserEntity.from(user), TargetType.POST, postId, request.getComment()));

        alarmProducer.send(new AlarmEvent(post.getAuthor().getId(), AlarmType.COMMENT, new AlarmArgs(userId, postId)));
    }

    @Override
    public List<Comment> getCommentListByPostId(Long postId) {
        return commentRepository.findByTargetIdAndTargetType(postId, TargetType.POST)
                .stream()
                .map(CommentEntity::toDomain)
                .toList();
    }

    public Post getPostByIdOrException(Long postId) {
        return postRepository.findById(postId)
                .map(PostEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_POST_NOT_FOUND, String.format("PostId %s is not found", postId)));
    }

}
