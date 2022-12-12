package fr.jadde.database.entity.scheduling;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weekly_recurrence")
public class WeeklyRecurrenceEntity extends AbstractRecurrenceEntity {
}