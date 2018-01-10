package com.epam.adok.core.repository;

import com.epam.adok.core.entity.comment.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}
