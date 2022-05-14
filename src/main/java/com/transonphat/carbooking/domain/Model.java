package com.transonphat.carbooking.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Author: Tran Son Phat
 * Base class of all entities
 * Has an id field and created date field
 */
@MappedSuperclass
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @CreatedDate
    @Column
    private ZonedDateTime createdDate;

    public Model() {
    }

    public Model(Long id, ZonedDateTime createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
