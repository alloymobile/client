package com.alloymobile.client.service.client;

import com.alloymobile.client.persistence.model.QClient;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public class ClientBinding  implements QuerydslBinderCustomizer<QClient> {
    @Override
    public void customize(QuerydslBindings querydslBindings, QClient qClient) {
        // Make case-insensitive 'like' filter for all string properties
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Predicate createSearchQuery(String search){
        BooleanBuilder where = new BooleanBuilder();
        QClient qClient = QClient.client;

        BooleanBuilder whereId = new BooleanBuilder();
        whereId.and(qClient.firstName.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qClient.lastName.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qClient.email.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qClient.phone.containsIgnoreCase(search));
        where.or(whereId);
        return where;
    }
}
