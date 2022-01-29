package com.alloymobile.client.service.role;

import com.alloymobile.client.persistence.model.QRole;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public class RoleBinding  implements QuerydslBinderCustomizer<QRole> {
    @Override
    public void customize(QuerydslBindings querydslBindings, QRole qRole) {
        // Make case-insensitive 'like' filter for all string properties
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Predicate createSearchQuery(String search){
        BooleanBuilder where = new BooleanBuilder();
        QRole qRole = QRole.role;

        BooleanBuilder whereId = new BooleanBuilder();
        whereId.and(qRole.name.containsIgnoreCase(search));
        where.or(whereId);

        return where;
    }
}
