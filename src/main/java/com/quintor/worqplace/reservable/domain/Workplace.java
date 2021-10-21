package com.quintor.worqplace.reservable.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    public Workplace(String floor, String reservableName, String reservableDescription, String chairDescription,
                     DeskType deskType) {
        super(floor, new ReservableInformation(reservableName, reservableDescription));
        this.chairDescription = chairDescription;
        this.deskType = deskType;
    }
}
