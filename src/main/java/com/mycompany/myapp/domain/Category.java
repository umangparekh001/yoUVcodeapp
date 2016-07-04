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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "category_title", length = 500, nullable = false)
    private String category_title;

    @NotNull
    @Column(name = "active_ind", nullable = false)
    private Boolean active_ind;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime created_date;

    @NotNull
    @Column(name = "last_chng_date", nullable = false)
    private ZonedDateTime last_chng_date;

    @ManyToOne
    @NotNull
    private User changed_by;

    @ManyToOne
    @NotNull
    private User last_chng_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public Boolean isActive_ind() {
        return active_ind;
    }

    public void setActive_ind(Boolean active_ind) {
        this.active_ind = active_ind;
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

    public User getChanged_by() {
        return changed_by;
    }

    public void setChanged_by(User user) {
        this.changed_by = user;
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
        Category category = (Category) o;
        if(category.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", category_title='" + category_title + "'" +
            ", active_ind='" + active_ind + "'" +
            ", created_date='" + created_date + "'" +
            ", last_chng_date='" + last_chng_date + "'" +
            '}';
    }
}
