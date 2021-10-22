package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservation.domain.Reservation;
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

    public Reservable(Long id, String floor, ReservableInformation info) {
        this.id = id;
        this.floor = floor;
        this.info = info;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
