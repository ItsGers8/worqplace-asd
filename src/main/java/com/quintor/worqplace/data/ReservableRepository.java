package com.quintor.worqplace.data;

import com.quintor.worqplace.domain.Reservable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservableRepository extends JpaRepository<Reservable, Long> {
}
