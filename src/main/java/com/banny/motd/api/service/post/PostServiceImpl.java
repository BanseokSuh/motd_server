package com.banny.motd.api.service.post;

import com.banny.motd.api.service.comment.CommentService;
import com.banny.motd.api.service.post.request.PostCreateServiceRequest;
import com.banny.motd.api.service.post.request.PostModifyServiceRequest;
import com.banny.motd.api.service.reaction.ReactionService;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.infrastructure.PostRepository;
import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.exception.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
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

        Post post = Post.builder()
                .author(user)
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .build();

        return postRepository.save(post);
    }

    @Override
    public List<Post> getPostList(SearchRequest request) {
        return postRepository.getPostList(request);
    }

    @Override
    public PostDetail getPost(Long postId) {
        Post post = postRepository.getById(postId);
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
        Post post = postRepository.getById(postId);
        User user = userService.getUserOrException(userId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_INVALID_PERMISSION, String.format("%s has no permission with the post", user.getLoginId()));
        }

        post.setPost(request.getContent());

        postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId) {
        User user = userService.getUserOrException(userId);
        Post post = postRepository.getById(postId);

        if (!post.isAuthor(user.getId())) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_INVALID_PERMISSION, String.format("%s has no permission with the post", user.getLoginId()));
        }

        postRepository.delete(post);
    }

}
