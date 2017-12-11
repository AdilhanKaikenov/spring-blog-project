package com.epam.adok.core.dao.impl.querybuilder;

import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.dao.impl.blog.BlogPredicates;
import com.epam.adok.core.entity.Category;
import com.epam.adok.core.util.DateRange;
import com.epam.adok.core.util.QueryDslUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static com.epam.adok.core.entity.QBlog.blog;

public class BlogQueryBuilder {

    private BlogFilter filter;
    private EntityManager entityManager;

    public BlogQueryBuilder(EntityManager entityManager, BlogFilter filter) {
        this.filter = filter;
        this.entityManager = entityManager;
    }

    public JPAQuery createJPAQuery() {
        final JPAQuery query = new JPAQuery(entityManager);
        query.from(blog);
        BooleanExpression predicate = handleFilterParameters(filter);
        return query.where(predicate);
    }

    private BooleanExpression handleFilterParameters(BlogFilter filter) {

        List<Category> categories = filter.getCategories();

        DateRange dateRange = filter.getDateRange();
        Date from = dateRange.getFrom();
        Date to = dateRange.getTo();

        BooleanExpression predicate = null;

        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                predicate = QueryDslUtil.and(predicate, BlogPredicates.isBlogHasCategory(blog, category));
            }
        }

        if (from != null) {
            predicate = QueryDslUtil.and(predicate, BlogPredicates.isPublishedDateAfter(blog, from));
        }

        if (to != null) {
            predicate = QueryDslUtil.and(predicate, BlogPredicates.isPublishedDateBefore(blog, to));
        }
        return predicate;
    }
}
