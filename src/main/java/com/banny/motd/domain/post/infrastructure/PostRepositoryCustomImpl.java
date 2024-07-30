package com.banny.motd.domain.post.infrastructure;

import com.banny.motd.domain.post.api.dto.request.PostSearchRequest;
import com.banny.motd.domain.post.infrastructure.entity.PostEntity;
import com.banny.motd.domain.post.infrastructure.entity.QPostEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostEntity> getPostList(PostSearchRequest request) {
        return jpaQueryFactory.selectFrom(QPostEntity.postEntity)
                .limit(request.getSize())
                .offset(request.getOffset())
                .orderBy(QPostEntity.postEntity.id.desc())
                .fetch();
    }
}
