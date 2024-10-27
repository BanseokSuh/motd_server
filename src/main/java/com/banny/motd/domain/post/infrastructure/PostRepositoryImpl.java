package com.banny.motd.domain.post.infrastructure;

import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.post.PostList;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.post.infrastructure.entity.QPostEntity;
import com.banny.motd.domain.user.infrastructure.eneity.QUserEntity;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.dto.response.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post getById(Long id) {
        return findById(id).orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_POST_NOT_FOUND, "The post is not found"));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public List<Post> getPostList(SearchRequest request) {
        return jpaQueryFactory
                .selectFrom(QPostEntity.postEntity)
                .limit(request.getSize())
                .offset(request.getOffset())
                .innerJoin(QUserEntity.userEntity)
                .on(QPostEntity.postEntity.user.id.eq(QUserEntity.userEntity.id))
                .orderBy(QPostEntity.postEntity.id.desc())
                .stream().map(PostEntity::toDomain)
                .toList();
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toDomain();
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(PostEntity.from(post));
    }

}
