package com.alloymobile.client.service;

import com.alloymobile.client.model.QCountry;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public class CountryBinding implements QuerydslBinderCustomizer<QCountry> {
    @Override
    public void customize(QuerydslBindings querydslBindings, QCountry qCountry) {
        // Make case-insensitive 'like' filter for all string properties
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Predicate createSearchQuery(String search){
        BooleanBuilder where = new BooleanBuilder();
        QCountry qCountry = QCountry.country;

        BooleanBuilder whereId = new BooleanBuilder();
        whereId.and(qCountry.name.containsIgnoreCase(search));
        where.or(qCountry.name.containsIgnoreCase(search));

        whereId = new BooleanBuilder();
        whereId.and(qCountry.code.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qCountry.dialCode.containsIgnoreCase(search));
        where.or(whereId);

        return where;
    }
}