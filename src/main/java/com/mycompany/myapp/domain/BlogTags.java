package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BlogTags.
 */
@Entity
@Table(name = "blog_tags")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "blogtags")
public class BlogTags implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 4000)
    @Column(name = "tags", length = 4000, nullable = false)
    private String tags;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "blog_tags_blog_id",
               joinColumns = @JoinColumn(name="blog_tags_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="blog_ids_id", referencedColumnName="ID"))
    private Set<Blog> blog_ids = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Set<Blog> getBlog_ids() {
        return blog_ids;
    }

    public void setBlog_ids(Set<Blog> blogs) {
        this.blog_ids = blogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlogTags blogTags = (BlogTags) o;
        if(blogTags.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, blogTags.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogTags{" +
            "id=" + id +
            ", tags='" + tags + "'" +
            '}';
    }
}
