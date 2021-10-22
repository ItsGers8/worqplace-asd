package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String floor;
    @Embedded
    private ReservableInformation info;
    @OneToMany(mappedBy = "reservable")
    private List<Reservation> reservations = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    public Reservable(String floor, ReservableInformation info) {
        this.floor = floor;
        this.info = info;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    public abstract Reservation reserve(Timeslot timeslot, boolean recurring) throws TimeslotOverlapException;
    public boolean isNotAvailableDuringTimeslot(Timeslot timeslot) {
        for (int i = 0; i < this.getReservations().size(); i++) {
            Reservation currentReservation = this.getReservations().get(i);
            Timeslot existing = currentReservation.getTimeslot();
            if (!timeslot.getDate().equals(existing.getDate())
                    || (currentReservation.isRecurring() && timeslot.getDate().getDayOfWeek()
                    != existing.getDate().getDayOfWeek())) continue;
            if (timeslot.getFromTime().isAfter(existing.getToTime())
                    && timeslot.getToTime().isAfter(existing.getToTime())) continue;
            if (timeslot.getFromTime().isBefore(existing.getFromTime())
                    && timeslot.getToTime().isBefore(existing.getFromTime())) continue;
            return true;
        }
        return false;
    }
}
