package com.quintor.worqplace.application.dto;

import com.quintor.worqplace.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationDTO {
    public Long id;
    public ReservableDTO reservable;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.reservable = reservation.getReservable().getDTO();
    }
}
