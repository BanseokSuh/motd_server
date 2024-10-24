package com.banny.motd.api.service.post;

import com.banny.motd.api.service.comment.CommentService;
import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.api.service.post.request.PostModifyServiceRequest;
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
import com.banny.motd.global.dto.response.ApiResponseStatusType;
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
    public Post createPost(PostCreateServiceRequest request, Long userId) {
        User user = userService.getUserOrException(userId);

        return postRepository.save(PostEntity.of(
                request.getImageUrls(), request.getContent(), UserEntity.from(user))).toDomain();
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
    public void modifyPost(Long postId, PostModifyServiceRequest request, Long userId) {
        User user = userService.getUserOrException(userId);
        Post post = getPostOrException(postId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_INVALID_PERMISSION, String.format("%s has no permission with the post", user.getLoginId()));
        }

        post.setPost(request.getContent());

        postRepository.saveAndFlush(PostEntity.from(post));
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId) {
        User user = userService.getUserOrException(userId);
        Post post = getPostOrException(postId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_INVALID_PERMISSION, String.format("%s has no permission with the post", user.getLoginId()));
        }

        postRepository.delete(PostEntity.from(post));
    }

    private Post getPostOrException(Long postId) {
        return postRepository.findById(postId)
                .map(PostEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_POST_NOT_FOUND, "The post is not found"));
    }

}
