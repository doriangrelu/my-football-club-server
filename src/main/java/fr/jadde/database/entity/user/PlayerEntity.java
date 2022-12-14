package fr.jadde.database.entity.user;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "player")
public class PlayerEntity extends AbstractUser {


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final PlayerEntity playerEntity = (PlayerEntity) o;
        return this.id != null && Objects.equals(this.id, playerEntity.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}