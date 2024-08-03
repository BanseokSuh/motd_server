package com.banny.motd.domain.reaction.application;

import com.banny.motd.domain.alarm.application.producer.AlarmProducer;
import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import com.banny.motd.domain.alarm.domain.event.AlarmEvent;
import com.banny.motd.domain.post.application.repository.PostRepository;
import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.reaction.domain.Reaction;
import com.banny.motd.domain.reaction.domain.ReactionType;
import com.banny.motd.domain.reaction.application.repository.ReactionRepository;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.enums.TargetType;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmProducer alarmProducer;

    @Override
    @Transactional
    public void likePost(Long postId, Long userId) {
        User user = getUserByIdOrException(userId);
        Post post = getPostByIdOrException(postId);

        Reaction like = getUserPostLike(userId, postId);

        /*
         * 기존 좋아요가 없으면 좋아요 추가
         * 기존 좋아요가 있으면 좋아요 삭제
         */
        if (like == null) {
            reactionRepository.save(ReactionEntity.of(UserEntity.from(user), TargetType.POST, postId, ReactionType.LIKE));
            alarmProducer.send(new AlarmEvent(post.getAuthor().getId(), AlarmType.LIKE, new AlarmArgs(userId, postId)));
        } else {
            reactionRepository.delete(ReactionEntity.from(like));
        }
    }

    @Override
    public List<Reaction> getLikeListByPostId(Long postId) {
        return reactionRepository.findListByTargetIdAndTargetTypeAndReactionType(postId, TargetType.POST, ReactionType.LIKE)
                .stream()
                .map(ReactionEntity::toDomain)
                .toList();
    }

    public User getUserByIdOrException(Long userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("UserId %s is not found", userId)));
    }

    public Post getPostByIdOrException(Long postId) {
        return postRepository.findById(postId)
                .map(PostEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ResultType.POST_NOT_FOUND, String.format("PostId %s is not found", postId)));
    }

    private Reaction getUserPostLike(Long userId, Long targetId) {
        return Optional.ofNullable(reactionRepository.findByUserIdAndTargetTypeAndTargetIdAndReactionType(userId, TargetType.POST, targetId, ReactionType.LIKE))
                .map(ReactionEntity::toDomain)
                .orElse(null);
    }
}
