package com.quintor.worqplace.reservation.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Timeslot {
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDate date;
}
