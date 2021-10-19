package com.quintor.worqplace.reservation.domain;

import com.quintor.worqplace.reservable.domain.Reservable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @EmbeddedId
    private ReservationId id;
    private boolean recurring;
    @Embedded
    private Timeslot timeslot;
    @ManyToOne
    @JoinColumn(name = "reservable_id", nullable = false)
    private Reservable reservable;

    public Reservation(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public void updateTimeslot(LocalDate newDate, LocalTime newFrom, LocalTime newTo){
        Timeslot newTimeslot = new Timeslot(newDate, newFrom, newTo);
        setTimeslot(newTimeslot);
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "timeslot=" + timeslot +
                '}';
    }
}
