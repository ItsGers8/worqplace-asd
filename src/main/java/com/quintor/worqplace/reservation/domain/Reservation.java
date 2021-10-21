package com.quintor.worqplace.reservation.domain;

import com.quintor.worqplace.reservable.domain.Reservable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
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

    public Reservation(Reservable reservable, Timeslot timeslot, boolean recurring) {
        this.reservable = reservable;
        this.timeslot = timeslot;
        this.recurring = recurring;
    }

    public Reservation() {

    }

    public void updateTimeslot(LocalDate newDate, LocalTime newFrom, LocalTime newTo){
        Timeslot newTimeslot = new Timeslot(newDate, newFrom, newTo);
        setTimeslot(newTimeslot);
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public ReservationId getId() {
        return id;
    }

    public void setId(ReservationId id) {
        this.id = id;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Reservable getReservable() {
        return reservable;
    }

    public void setReservable(Reservable reservable) {
        this.reservable = reservable;
    }
}
