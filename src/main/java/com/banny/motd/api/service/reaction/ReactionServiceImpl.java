package com.banny.motd.api.service.reaction;

import com.banny.motd.api.service.alarm.producer.AlarmProducer;
import com.banny.motd.domain.alarm.AlarmArgs;
import com.banny.motd.domain.alarm.AlarmEvent;
import com.banny.motd.domain.alarm.AlarmType;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.domain.reaction.ReactionType;
import com.banny.motd.domain.reaction.infrastructure.ReactionRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.enums.TargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        User user = userRepository.getById(userId);
        Post post = postRepository.getById(postId);
        Reaction like = getUserPostLike(userId, postId);

        /*
         * 기존 좋아요가 없으면 좋아요 추가
         * 기존 좋아요가 있으면 좋아요 삭제
         */
        if (like == null) {
            Reaction newLike = Reaction.of(user, TargetType.POST, postId, ReactionType.LIKE);
            reactionRepository.save(newLike);
            alarmProducer.send(AlarmEvent.of(post.getAuthor().getId(), AlarmType.LIKE, AlarmArgs.of(userId, TargetType.POST, postId)));
        } else {
            reactionRepository.delete(like);
        }
    }

    @Override
    public List<Reaction> getLikeListByPostId(Long postId) {
        return reactionRepository.findListByTargetIdAndTargetTypeAndReactionType(postId, TargetType.POST, ReactionType.LIKE);
    }

    private Reaction getUserPostLike(Long userId, Long targetId) {
        return reactionRepository.findByUserIdAndTargetTypeAndTargetIdAndReactionType(userId, TargetType.POST, targetId, ReactionType.LIKE);
    }

}
