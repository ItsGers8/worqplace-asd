package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
    ReservationRepository repository;

    public List<Reservation> getReservationsAtTimeslot(Timeslot timeslot) {
        return repository.findAll()
                .stream()
                .filter(reservation -> reservation.getTimeslot().equals(timeslot))
                .collect(Collectors.toList());
    }

}
