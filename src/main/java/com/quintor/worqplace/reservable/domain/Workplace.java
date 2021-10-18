package com.quintor.worqplace.reservable.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Workplace extends Reservable {
    private String chairDescription;
    private DeskType deskType;
}
