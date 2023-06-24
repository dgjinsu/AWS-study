package linklibrary.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import linklibrary.dto.response.PostDto1;
import linklibrary.dto.response.QPostDto1;
import linklibrary.entity.QProfileImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static linklibrary.entity.QCategory.*;
import static linklibrary.entity.QPost.*;
import static linklibrary.entity.QProfileImg.*;
import static linklibrary.entity.QUser.*;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<PostDto1> findPostDtos(Long userId, String bookmark, String sort, String keyword, Long categoryId, Pageable pageable) {
        List<PostDto1> result = queryFactory
                .select(new QPostDto1(
                        post.id,
                        post.title,
                        post.memo,
                        post.url,
                        post.bookmark,
                        user.nickname,
                        post.updatedAt,
                        category.name,
                        profileImg.storeFileName,
                        category.id
                ))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .leftJoin(post.user.profileImg, profileImg) //profileImg 는 null 일수도 있기 때문에 leftJoin
                .where(
                        userIdEq(userId),
                        postTitleEq(keyword),
                        bookmarkEq(bookmark),
                        categoryIdEq(categoryId)
                )
                .orderBy(postOrderBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .leftJoin(post.user.profileImg, profileImg) // profileImg는 null 일수도 있기 때문에 leftJoin
                .where(
                        userIdEq(userId),
                        postTitleEq(keyword),
                        bookmarkEq(bookmark),
                        categoryIdEq(categoryId)
                )
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? post.category.id.eq(categoryId) : null;
    }

    private OrderSpecifier postOrderBy(String sort) {
        if(sort.equals("byDate")) return post.createdAt.desc();
        else if(sort.equals("byTitle")) return post.title.asc();
        return null;
    }

    private BooleanExpression bookmarkEq(String bookmark) {
        if(bookmark == null) return null;
        return bookmark.equals("true") ? post.bookmark.eq(true) : post.bookmark.eq(false);
    }

    private BooleanExpression postTitleEq(String keyword) {
        return keyword.equals("") ? null : post.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? post.user.id.eq(userId) : null;
    }
}
