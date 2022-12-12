package fr.jadde.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "teams")
public class TeamEntity extends PanacheEntity {

    @Column(name = "name", unique = true)
    private String name;


    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final TeamEntity teamEntity = (TeamEntity) o;
        return this.id != null && Objects.equals(this.id, teamEntity.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}