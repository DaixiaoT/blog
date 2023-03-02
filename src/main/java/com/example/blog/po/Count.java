package com.example.blog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_count")
public class Count {
    @Id
    @GeneratedValue
    private Long id;

    private Long indexCount;

    private Long aboutCount;

    private Long archivesCount;

    private Long tagsCount;

    private Long typesCount;

    private Long searchCount;

    public Long getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Long searchCount) {
        this.searchCount = searchCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(Long indexCount) {
        this.indexCount = indexCount;
    }

    public Long getAboutCount() {
        return aboutCount;
    }

    public void setAboutCount(Long aboutCount) {
        this.aboutCount = aboutCount;
    }

    public Long getArchivesCount() {
        return archivesCount;
    }

    public void setArchivesCount(Long archivesCount) {
        this.archivesCount = archivesCount;
    }

    public Long getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(Long tagsCount) {
        this.tagsCount = tagsCount;
    }

    public Long getTypesCount() {
        return typesCount;
    }

    public void setTypesCount(Long typesCount) {
        this.typesCount = typesCount;
    }
}
