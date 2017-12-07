package com.epam.adok.core.dao.impl.blog;

import com.epam.adok.core.entity.Category;
import com.epam.adok.core.entity.QBlog;
import com.mysema.query.types.expr.BooleanExpression;

import java.util.Date;

public final class BlogPredicates {

    public static BooleanExpression isBlogHasCategory(QBlog qBlog, Category category) {
        return qBlog.categories.contains(category);
    }

    public static BooleanExpression isPublishedDateAfter(QBlog qBlog, Date from) {
        return qBlog.publicationDate.after(from);
    }

    public static BooleanExpression isPublishedDateBefore(QBlog qBlog, Date to) {
        return qBlog.publicationDate.before(to);
    }

}
