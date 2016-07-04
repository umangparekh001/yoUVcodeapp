package com.mycompany.myapp.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the BlogStatus entity.
 */
public class BlogStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String blog_status;

    @NotNull
    private ZonedDateTime created_date;

    @NotNull
    private ZonedDateTime last_chng_date;


    private Long created_byId;
    

    private String created_byLogin;

    private Long last_chng_byId;
    

    private String last_chng_byLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getBlog_status() {
        return blog_status;
    }

    public void setBlog_status(String blog_status) {
        this.blog_status = blog_status;
    }
    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }
    public ZonedDateTime getLast_chng_date() {
        return last_chng_date;
    }

    public void setLast_chng_date(ZonedDateTime last_chng_date) {
        this.last_chng_date = last_chng_date;
    }

    public Long getCreated_byId() {
        return created_byId;
    }

    public void setCreated_byId(Long userId) {
        this.created_byId = userId;
    }


    public String getCreated_byLogin() {
        return created_byLogin;
    }

    public void setCreated_byLogin(String userLogin) {
        this.created_byLogin = userLogin;
    }

    public Long getLast_chng_byId() {
        return last_chng_byId;
    }

    public void setLast_chng_byId(Long userId) {
        this.last_chng_byId = userId;
    }


    public String getLast_chng_byLogin() {
        return last_chng_byLogin;
    }

    public void setLast_chng_byLogin(String userLogin) {
        this.last_chng_byLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogStatusDTO blogStatusDTO = (BlogStatusDTO) o;

        if ( ! Objects.equals(id, blogStatusDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogStatusDTO{" +
            "id=" + id +
            ", blog_status='" + blog_status + "'" +
            ", created_date='" + created_date + "'" +
            ", last_chng_date='" + last_chng_date + "'" +
            '}';
    }
}
