package com.quintor.worqplace.application.dto;

import com.quintor.worqplace.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomDTO extends ReservableDTO {
    public List<WorkplaceDTO> workplaces;
    public RoomDTO(Long id, String name) {
        super(id, name);
    }
    public RoomDTO(Room room) {
        super(room.getId(), room.getName());
        workplaces = room.getWorkplaces().stream().map(WorkplaceDTO::new).collect(Collectors.toList());
    }
}
