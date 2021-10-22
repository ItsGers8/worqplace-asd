package com.quintor.worqplace.reservation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
//    Optional<Client> findByCompany_name(String name);
    Optional<Reservation> findByReservationId(ReservationId id);
}
