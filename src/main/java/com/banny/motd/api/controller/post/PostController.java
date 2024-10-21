package com.banny.motd.api.controller.post;

import com.banny.motd.api.controller.post.dto.request.PostCreateRequest;
import com.banny.motd.api.controller.post.dto.request.PostModifyRequest;
import com.banny.motd.api.controller.post.dto.response.PostCreateResponse;
import com.banny.motd.api.controller.post.dto.response.PostDetailResponse;
import com.banny.motd.api.controller.post.dto.response.PostListResponse;
import com.banny.motd.api.service.post.PostService;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostCreateResponse> createPost(@Valid @RequestBody PostCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Post post = postService.createPost(request.getImageUrls(), request.getContent(), user.getId());

        return Response.success(PostCreateResponse.from(post));
    }

    @GetMapping
    public Response<List<PostListResponse>> getPostList(SearchRequest request) {
        List<PostList> posts = postService.getPostList(request);

        List<PostListResponse> postResponses = posts.stream()
                .map(PostListResponse::from).toList();

        return Response.success(postResponses);
    }

    @GetMapping("/{id}")
    public Response<PostDetailResponse> getPost(@PathVariable Long id) {
        PostDetail post = postService.getPost(id);

        return Response.success(PostDetailResponse.from(post));
    }

    @PutMapping("/{id}")
    public Response<Void> modifyPost(@PathVariable Long id, @RequestBody PostModifyRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.modifyPost(id, request.getContent(), user.getId());

        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.deletePost(id, user.getId());

        return Response.success();
    }

}
