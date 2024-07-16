package com.lightcc.motd.domain.like.application;

import com.lightcc.motd.domain.like.domain.repository.LikeRepository;
import com.lightcc.motd.domain.like.infrastructure.entity.LikeEntity;
import com.lightcc.motd.domain.post.domain.repository.PostRepository;
import com.lightcc.motd.domain.post.infrastructure.entity.PostEntity;
import com.lightcc.motd.domain.user.domain.repository.UserRepository;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import com.lightcc.motd.global.exception.ApplicationException;
import com.lightcc.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void likePost(Long postId, Long userId) {
        UserEntity userEntity = getUserEntityOrException(userId);
        PostEntity postEntity = getPostEntityOrException(postId);

        if (likeRepository.findByUserAndPost(userEntity, postEntity).isEmpty()) {
            // 좋아요를 누르지 않았다면
            likeRepository.save(LikeEntity.of(userEntity, postEntity));
        } else {
            // 좋아요를 누른 상태라면
            likeRepository.deleteByUserAndPost(userEntity, postEntity);
        }
    }

    public UserEntity getUserEntityOrException(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("UserId %s is not found", userId)));
    }

    public PostEntity getPostEntityOrException(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ResultType.POST_NOT_FOUND, String.format("PostId %s is not found", postId)));
    }
}
