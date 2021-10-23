package com.quintor.worqplace.reservation.domain;

import com.quintor.worqplace.reservable.domain.Reservable;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public Reservation(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Timeslot updateTimeslot(LocalDate newDate, LocalTime newFrom, LocalTime newTo) throws InvalidTimeslotException {
        Timeslot newTimeslot = new Timeslot(newDate, newFrom, newTo);
        setTimeslot(newTimeslot);
        return newTimeslot;
    }
}
