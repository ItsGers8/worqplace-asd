package com.quintor.worqplace.reservation.domain;

import com.quintor.worqplace.reservable.domain.Reservable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @EmbeddedId
    private ReservationId id;
    private boolean recurring;
    @Embedded
    private Timeslot timeslot;
    @ManyToOne
    @JoinColumn(name = "reservable_id", nullable = false)
    private Reservable reservable;
}
