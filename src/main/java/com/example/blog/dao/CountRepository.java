package com.example.blog.dao;

import com.example.blog.po.Blog;
import com.example.blog.po.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostUpdate;

public interface CountRepository extends JpaRepository<Count, Long> {
    //    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
//    Page<Blog> findByQuery(String query, Pageable pageable);
    @Transactional
    @Modifying
    @Query("update Count c set c.indexCount=c.indexCount+1 where  c.id=?1")
    int updateIndexCount(Long id);

    @Transactional
    @Modifying
    @Query("update Count c set c.typesCount=c.typesCount+1 where  c.id=?1")
    int updateTypeCount(Long id);

    @Transactional
    @Modifying
    @Query("update Count c set c.tagsCount=c.tagsCount+1 where  c.id=?1")
    int updateTagCount(Long id);

    @Transactional
    @Modifying
    @Query("update Count c set c.archivesCount=c.archivesCount+1 where  c.id=?1")
    int updateArchiveCount(Long id);

    @Transactional
    @Modifying
    @Query("update Count c set c.aboutCount=c.aboutCount+1 where  c.id=?1")
    int updateAboutCount(Long id);

    @Query("select indexCount from Count where id=?1")
    Long findIndexCount(Long id);

    @Query("select aboutCount from Count where id=?1")
    Long findAboutCount(Long id);

    @Query("select tagsCount from Count where id=?1")
    Long findTagsCount(Long id);

    @Query("select typesCount from Count where id=?1")
    Long findTypeCount(Long id);

    @Query("select archivesCount from Count where id=?1")
    Long findArchiveCount(Long id);

    @Transactional
    @Modifying
    @Query("update Count c set c.searchCount=c.searchCount+1 where  c.id=?1")
    int updateSearchCount(Long id);

    @Query("select searchCount from Count where id=?1")
    Long findSearchCount(Long id);

}
