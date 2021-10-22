package com.quintor.worqplace.reservation.application.DTO;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationId;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationDTO {
    public ReservationId id;

    public Timeslot timeslot;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.timeslot = reservation.getTimeslot();
    }
}