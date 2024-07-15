package com.lightcc.motd.domain.post.api;

import com.lightcc.motd.domain.post.api.dto.request.PostCreateRequest;
import com.lightcc.motd.domain.post.api.dto.request.PostModifyRequest;
import com.lightcc.motd.domain.post.api.dto.response.PostCreateResponse;
import com.lightcc.motd.domain.post.api.dto.response.PostResponse;
import com.lightcc.motd.domain.post.application.PostService;
import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Post post = postService.createPost(request.getTitle(), request.getContent(), user.getId());

        return Response.success(PostCreateResponse.from(post));
    }

    @GetMapping
    public Response<Page<PostResponse>> getPostList(Pageable pageable) {
        Page<Post> posts = postService.getPostList(pageable);

        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/{id}")
    public Response<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);

        return Response.success(PostResponse.from(post));
    }

    @PutMapping("/{id}")
    public Response<Void> modifyPost(@PathVariable Long id, @RequestBody PostModifyRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.modifyPost(id, request.getTitle(), request.getContent(), user.getId());

        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.deletePost(id, user.getId());

        return Response.success();
    }

    @PostMapping("/{id}/like")
    public Response<Void> likePost(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postService.likePost(id, user.getId());

        return Response.success();
    }
}
