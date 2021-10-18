package com.quintor.worqplace.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ReservationId implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
