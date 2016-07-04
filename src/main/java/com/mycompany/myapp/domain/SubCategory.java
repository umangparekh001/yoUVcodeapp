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
 * A SubCategory.
 */
@Entity
@Table(name = "sub_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "subcategory")
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "sub_category_title", length = 1000, nullable = false)
    private String sub_category_title;

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
    private Category category_id;

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

    public String getSub_category_title() {
        return sub_category_title;
    }

    public void setSub_category_title(String sub_category_title) {
        this.sub_category_title = sub_category_title;
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

    public Category getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category category) {
        this.category_id = category;
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

    public void setLast_chng_by(User last_chng_by) {
        this.last_chng_by = last_chng_by;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubCategory subCategory = (SubCategory) o;
        if(subCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubCategory{" +
            "id=" + id +
            ", sub_category_title='" + sub_category_title + "'" +
            ", active_ind='" + active_ind + "'" +
            ", created_date='" + created_date + "'" +
            ", last_chng_date='" + last_chng_date + "'" +
            '}';
    }
}
