package com.epam.adok.core.repository;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.vo.BlogTitleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(name = "Blog.readVOByTitle")
    BlogTitleVO getBlogByTitle(@Param("title") String title);

    Blog findByTitle(String title);
}
