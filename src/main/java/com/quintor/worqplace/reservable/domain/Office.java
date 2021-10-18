package com.quintor.worqplace.reservable.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    @EmbeddedId
    private OfficeId id;
    private String name;
    @Embedded
    private OfficeAddress location;
    @OneToMany(mappedBy = "office")
    private List<Reservable> reservables;
}
