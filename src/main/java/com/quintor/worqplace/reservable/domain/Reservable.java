package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservable {
    @EmbeddedId
    private ReservableId id;
    private String floor;
    @Embedded
    private ReservableInformation info;
    @OneToMany(mappedBy = "reservable")
    private List<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;
}
