package com.quintor.worqplace.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservable_id")
    private Reservable reservable;

    public Reservation(Reservable reservable) {
        this.reservable = reservable;
    }
}
