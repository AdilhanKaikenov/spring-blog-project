package com.epam.adok.core.util;

import com.mysema.query.types.expr.BooleanExpression;

public final class QueryDslUtil {

    private QueryDslUtil() {
    }

    public static BooleanExpression and(final BooleanExpression left, final BooleanExpression right) {
        return left == null ? right : right == null ? left : left.and(right);
    }

    public static BooleanExpression or(final BooleanExpression left, final BooleanExpression right) {
        return left == null ? right : right == null ? left : left.or(right);
    }

}
