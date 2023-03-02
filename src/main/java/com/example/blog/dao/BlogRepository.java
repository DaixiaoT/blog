package com.example.blog.dao;

import com.example.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where  b.id=?1")
    int updateViews(Long id);
    //      SELECT          DATE_FORMAT(b.update_time,'%Y') as YEAR from t_blog b GROUP BY YEAR ORDER BY YEAR DESC;
    @Query(value = "SELECT  DATE_FORMAT(b.create_time,'%Y') as YEAR from t_blog b GROUP BY YEAR ORDER BY YEAR desc ",nativeQuery = true)
    List<String> findGroupYear();
    @Query("select b from Blog b where function('date_format',b.createTime,'%Y')=?1 ")
    List<Blog> findByYear(String year);
}
