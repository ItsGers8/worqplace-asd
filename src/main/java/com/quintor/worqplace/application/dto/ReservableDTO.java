package com.quintor.worqplace.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class ReservableDTO {
    public Long id;
    public String name;
}
