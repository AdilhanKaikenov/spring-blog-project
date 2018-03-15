package com.epam.adok.core.repository;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.comment.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    Page<BlogComment> findAllByBlog(Blog blog, Pageable pageable);

    int countByBlog(Blog blog);

    @Query("SELECT comment FROM BlogComment comment WHERE comment.blog.id = :blogId AND comment.parentComment.id IS NULL ORDER BY comment.commentDate DESC")
    Page<BlogComment> findAllRootBlogCommentsByBlog(@Param("blogId") long blogId, Pageable pageable);

    List<BlogComment> findAllInclusiveByBlogAndParentCommentIdIn(Blog blog, List<Long> parentsIdList, Pageable pageable);

}
