package com.quintor.worqplace.domain;

import com.quintor.worqplace.application.dto.ReservableDTO;
import com.quintor.worqplace.application.dto.RoomDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Room extends Reservable {
    @OneToMany(mappedBy = "room")
    private List<Workplace> workplaces;

    public Room(String name) {
        super(name);
    }

    public Room() {

    }

    @Override
    public ReservableDTO getDTO() {
        return new RoomDTO(this);
    }
}
