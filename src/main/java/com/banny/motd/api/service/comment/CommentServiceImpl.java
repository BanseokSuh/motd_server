package com.banny.motd.api.service.comment;

import com.banny.motd.api.service.alarm.producer.AlarmProducer;
import com.banny.motd.api.service.comment.request.CommentCreateServiceRequest;
import com.banny.motd.domain.alarm.AlarmArgs;
import com.banny.motd.domain.alarm.AlarmEvent;
import com.banny.motd.domain.alarm.AlarmType;
import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.comment.infrastructure.CommentRepository;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.enums.TargetType;
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
    public void createPostComment(Long postId, Long userId, CommentCreateServiceRequest request) {
        Post post = postRepository.getById(postId);
        User author = userRepository.getById(userId);
        Comment comment = Comment.builder()
                .author(author)
                .targetType(TargetType.POST)
                .targetId(postId)
                .comment(request.getComment())
                .build();

        commentRepository.save(comment);

        AlarmEvent alarmEvent = AlarmEvent.builder()
                .receiverUserId(post.getAuthor().getId())
                .alarmType(AlarmType.COMMENT)
                .alarmArgs(AlarmArgs.builder().fromUserId(userId).targetId(postId).build())
                .build();
        alarmProducer.send(alarmEvent);
    }

    @Override
    public List<Comment> getCommentListByPostId(Long postId) {
        return commentRepository.findByTargetIdAndTargetType(postId, TargetType.POST);
    }

}
