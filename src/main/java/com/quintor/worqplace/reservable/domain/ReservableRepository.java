package com.quintor.worqplace.reservable.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservableRepository extends JpaRepository<Reservable, Long> {
}
