package com.quintor.worqplace.domain;

import com.quintor.worqplace.application.dto.ReservableDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Reservable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "reservable")
    private List<Reservation> reservations = new ArrayList<>();

    public Reservable(String name) {
        this.name = name;
    }

    public Reservable() {

    }

    public abstract ReservableDTO getDTO();
}
