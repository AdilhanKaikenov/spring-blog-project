package com.epam.adok.core.dao;

import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;

import java.util.List;

public interface CommentDao<T extends AbstractComment> extends Dao<T> {

    List<BlogComment> readAllByBlogId(int id);

}
