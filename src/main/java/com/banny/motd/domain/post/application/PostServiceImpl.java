package com.banny.motd.domain.post.application;

import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.post.domain.repository.PostRepository;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.user.domain.repository.UserRepository;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Post createPost(String title, String content, Long userId) {
        getUserEntityOrException(userId);

        return postRepository.save(PostEntity.of(title, content, userId)).toDomain();
    }

    @Override
    public Page<Post> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostEntity::toDomain);
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

        if (!postEntity.getUserId().equals(userEntity.getId())) {
            throw new ApplicationException(ResultType.INVALID_PERMISSION, String.format("UserId %s has no permission with PostId %s", userId, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        postRepository.saveAndFlush(postEntity);
    }

    @Override
    public void deletePost(Long postId, Long userId) {
        UserEntity userEntity = getUserEntityOrException(userId);
        PostEntity postEntity = getPostEntityOrException(postId);

        if (!postEntity.getUserId().equals(userEntity.getId())) {
            throw new ApplicationException(ResultType.INVALID_PERMISSION, String.format("UserId %s has no permission with PostId %s", userId, postId));
        }

        postRepository.delete(postEntity);
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
