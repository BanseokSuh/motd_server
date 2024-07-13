package com.lightcc.motd.domain.post.application;

import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.domain.post.domain.repository.PostRepository;
import com.lightcc.motd.domain.post.infrastructure.entity.PostEntity;
import com.lightcc.motd.domain.user.domain.repository.UserRepository;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import com.lightcc.motd.global.exception.ApplicationException;
import com.lightcc.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Post createPost(String title, String content, Long userId) {
        UserEntity userEntity = getUserEntityOrException(userId);

        return postRepository.save(PostEntity.of(title, content, userEntity)).toDomain();
    }

    @Override
    public Post getPost(Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);

        return postEntity.toDomain();
    }

    @Override
    public void modifyPost(Long postId, String title, String content, Long userId) {
        UserEntity userEntity = getUserEntityOrException(userId);
        PostEntity postEntity = getPostEntityOrException(postId);

        if (!postEntity.getUser().equals(userEntity)) {
            throw new ApplicationException(ResultType.INVALID_PERMISSION, String.format("UserId %s has no permission with PostId %s", userId, postId));
        }

        // todo: 비즈니스 로직은 도메인 객체가 처리하도록 수정
        postEntity.setTitle(title);
        postEntity.setContent(content);

        postRepository.saveAndFlush(postEntity);
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
