package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BlogStatus.
 */
@Entity
@Table(name = "blog_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "blogstatus")
public class BlogStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "blog_status", length = 100, nullable = false)
    private String blog_status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime created_date;

    @NotNull
    @Column(name = "last_chng_date", nullable = false)
    private ZonedDateTime last_chng_date;

    @ManyToOne
    @NotNull
    private User created_by;

    @ManyToOne
    @NotNull
    private User last_chng_by;

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

    public User getCreated_by() {
        return created_by;
    }

    public void setCreated_by(User user) {
        this.created_by = user;
    }

    public User getLast_chng_by() {
        return last_chng_by;
    }

    public void setLast_chng_by(User user) {
        this.last_chng_by = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlogStatus blogStatus = (BlogStatus) o;
        if(blogStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, blogStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogStatus{" +
            "id=" + id +
            ", blog_status='" + blog_status + "'" +
            ", created_date='" + created_date + "'" +
            ", last_chng_date='" + last_chng_date + "'" +
            '}';
    }
}
