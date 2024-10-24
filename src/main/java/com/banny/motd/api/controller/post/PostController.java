package com.banny.motd.api.controller.post;

import com.banny.motd.api.controller.post.request.PostCreateRequest;
import com.banny.motd.api.controller.post.request.PostModifyRequest;
import com.banny.motd.api.controller.post.response.PostCreateResponse;
import com.banny.motd.api.controller.post.response.PostDetailResponse;
import com.banny.motd.api.controller.post.response.PostListResponse;
import com.banny.motd.api.service.post.PostService;
import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostDetail;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.dto.response.ApiResponse;
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
    public ApiResponse<PostCreateResponse> createPost(@Valid @RequestBody PostCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Post post = postService.createPost(request.toServiceRequest(), user.getId());

        return ApiResponse.ok(PostCreateResponse.from(post));
    }

    @GetMapping
    public ApiResponse<List<PostListResponse>> getPostList(SearchRequest request) {
        List<PostList> posts = postService.getPostList(request);

        List<PostListResponse> postResponses = posts.stream()
                .map(PostListResponse::from).toList();

        return ApiResponse.ok(postResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long id) {
        PostDetail post = postService.getPost(id);

        return ApiResponse.ok(PostDetailResponse.from(post));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> modifyPost(@PathVariable Long id, @RequestBody PostModifyRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.modifyPost(id, request.toServiceRequest(), user.getId());

        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.deletePost(id, user.getId());

        return ApiResponse.ok();
    }

}
