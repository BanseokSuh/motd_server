package com.banny.motd.api.service.post;

import com.banny.motd.api.service.comment.CommentService;
import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.api.service.reaction.ReactionService;
import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ReactionService reactionService;
    private final CommentService commentService;

    @Override
    @Transactional
    public Post createPost(List<String> imageUrls, String content, Long userId) {
        User user = userService.getUserOrException(userId);

        return postRepository.save(PostEntity.of(imageUrls, content, UserEntity.from(user))).toDomain();
    }

    @Override
    public List<PostList> getPostList(SearchRequest request) {
        return postRepository.getPostListCustom(request)
                .stream()
                .map(postEntity -> PostList.builder()
                        .post(postEntity.toDomain())
                        .user(postEntity.getUser().toDomain())
                        .build())
                .toList();
    }

    @Override
    public PostDetail getPost(Long postId) {
        Post post = getPostOrException(postId);
        log.info("[2] post: {}", post);

        User user = userService.getUserOrException(post.getAuthor().getId());


        List<Reaction> likeList = reactionService.getLikeListByPostId(postId);
        List<Comment> commentList = commentService.getCommentListByPostId(postId);

        return PostDetail.builder()
                .post(post)
                .user(user)
                .commentList(commentList)
                .likeList(likeList)
                .build();
    }

    @Override
    @Transactional
    public void modifyPost(Long postId, String content, Long userId) {
        User user = userService.getUserOrException(userId);
        Post post = getPostOrException(postId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(StatusType.FAIL_INVALID_PERMISSION, String.format("UserId %s has no permission with PostId %s", userId, postId));
        }

        post.setPost(content);

        postRepository.saveAndFlush(PostEntity.from(post));
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId) {
        User user = userService.getUserOrException(userId);
        Post post = getPostOrException(postId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(StatusType.FAIL_INVALID_PERMISSION, String.format("UserId %s has no permission with PostId %s", userId, postId));
        }

        postRepository.delete(PostEntity.from(post));
    }

    private Post getPostOrException(Long postId) {
        log.info("[1] postId: {}", postId);

        return postRepository.findById(postId)
                .map(PostEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(StatusType.FAIL_POST_NOT_FOUND, String.format("PostId %s is not found", postId)));
    }

}
