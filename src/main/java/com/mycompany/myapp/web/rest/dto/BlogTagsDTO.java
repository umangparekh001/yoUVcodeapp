package com.mycompany.myapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the BlogTags entity.
 */
public class BlogTagsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 4000)
    private String tags;


    private Set<BlogDTO> blog_ids = new HashSet<>();

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

    public Set<BlogDTO> getBlog_ids() {
        return blog_ids;
    }

    public void setBlog_ids(Set<BlogDTO> blogs) {
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

        BlogTagsDTO blogTagsDTO = (BlogTagsDTO) o;

        if ( ! Objects.equals(id, blogTagsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogTagsDTO{" +
            "id=" + id +
            ", tags='" + tags + "'" +
            '}';
    }
}
