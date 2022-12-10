package com.example.vuebackboard.entity;

import com.example.vuebackboard.model.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.vuebackboard.entity.QCheckAnswerEntity.checkAnswerEntity;

@RequiredArgsConstructor
@Repository
public class CheckAnswerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<CheckAnswerEntity> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {
        JPAQuery<CheckAnswerEntity> query = queryFactory.selectFrom(checkAnswerEntity)
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()));

        long total = query.stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<CheckAnswerEntity> results = query
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(checkAnswerEntity.idx.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchKeywords(String sk1, String sv1) {
        if("problemid".equals(sk1)) {
            if(StringUtils.hasLength(sv1)) {
                return checkAnswerEntity.problemid.eq(sv1);
            }
        }

        return null;
    }
}
