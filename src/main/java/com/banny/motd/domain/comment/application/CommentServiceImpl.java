package com.banny.motd.domain.comment.application;

import com.banny.motd.domain.alarm.application.producer.AlarmProducer;
import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import com.banny.motd.domain.alarm.domain.event.AlarmEvent;
import com.banny.motd.domain.comment.application.repository.CommentRepository;
import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.banny.motd.domain.post.application.repository.PostRepository;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.global.enums.TargetType;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmProducer alarmProducer;

    @Override
    @Transactional
    public void commentPost(Long postId, Long userId, String comment) {
        checkUserExistById(userId);

        PostEntity postEntity = getPostEntityById(postId);

        commentRepository.save(CommentEntity.of(userId, TargetType.POST, postId, comment));

        alarmProducer.send(new AlarmEvent(postEntity.getUserId(), AlarmType.COMMENT, new AlarmArgs(userId, postId)));
    }

    public void checkUserExistById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("UserId %s is not found", userId)));
    }

    public PostEntity getPostEntityById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ResultType.POST_NOT_FOUND, String.format("PostId %s is not found", postId)));
    }
}
