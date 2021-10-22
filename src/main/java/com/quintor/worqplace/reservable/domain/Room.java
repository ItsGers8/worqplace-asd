package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends Reservable {
    private boolean hasScreen;
    private boolean canBeLocked;
    @OneToMany(mappedBy = "room")
    private List<Workplace> workplaces = new ArrayList<>();

    public Room(String floor, String name, String description, boolean hasScreen, boolean canBeLocked) {
        super(floor, new ReservableInformation(name, description));
        this.hasScreen = hasScreen;
        this.canBeLocked = canBeLocked;
    }

    public void addWorkplace(Workplace workplace) {
        this.workplaces.add(workplace);
    }

    @Override
    public Reservation reserve(Timeslot timeslot, boolean recurring) throws TimeslotOverlapException {
        if (this.isNotAvailableDuringTimeslot(timeslot) ||
                this.workplaces.stream().anyMatch(workplace -> workplace.isNotAvailableDuringTimeslot(timeslot))) {
            throw new TimeslotOverlapException("This room, or one of the workplaces in it " +
                    "is already reserved during that timeslot");
        }
        Reservation reservation = new Reservation(this, timeslot, recurring);
        addReservation(reservation);
        return reservation;
    }
}
