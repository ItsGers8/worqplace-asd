package com.quintor.worqplace.domain;

import com.quintor.worqplace.application.dto.ReservableDTO;
import com.quintor.worqplace.application.dto.WorkplaceDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Workplace extends Reservable {
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public Workplace(String name, Room room) {
        super(name);
        this.room = room;
    }

    public Workplace() {
    }

    @Override
    public ReservableDTO getDTO() {
        return new WorkplaceDTO(this);
    }
}
