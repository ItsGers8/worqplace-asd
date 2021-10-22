package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workplace extends Reservable {
    private String chairDescription;
    private DeskType deskType;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Workplace(Long id, String floor, ReservableInformation info, List<Reservation> reservations,
                     Office office, String chairDescription, DeskType deskType, Room room) {
        super(id, floor, info, reservations, office);
        this.chairDescription = chairDescription;
        this.deskType = deskType;
        this.room = room;
    }

    public Workplace(String floor, String reservableName, String reservableDescription, String chairDescription,
                     DeskType deskType) {
        super(floor, new ReservableInformation(reservableName, reservableDescription));
        this.chairDescription = chairDescription;
        this.deskType = deskType;
    }

    @Override
    public Reservation reserve(Timeslot timeslot, boolean recurring) throws TimeslotOverlapException {
        if (this.isNotAvailableDuringTimeslot(timeslot) || (this.room != null &&
                this.room.isNotAvailableDuringTimeslot(timeslot))) {
            throw new TimeslotOverlapException("Workplace is occupied during that timeslot");
        }
        Reservation reservation = new Reservation(this, timeslot, recurring);
        this.addReservation(reservation);
        return reservation;
    }
}
