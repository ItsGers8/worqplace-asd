package com.quintor.worqplace.application.dto;

import com.quintor.worqplace.domain.Workplace;

public class WorkplaceDTO extends ReservableDTO {
    public WorkplaceDTO(Long id, String name) {
        super(id, name);
    }

    public WorkplaceDTO(Workplace workplace) {
        super(workplace.getId(), workplace.getName());
    }
}
