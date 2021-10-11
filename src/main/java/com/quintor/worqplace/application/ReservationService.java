package com.quintor.worqplace.application;

import com.quintor.worqplace.application.dto.ReservationDTO;
import com.quintor.worqplace.application.exception.ReservationNotFoundException;
import com.quintor.worqplace.data.ReservableRepository;
import com.quintor.worqplace.data.ReservationRepository;
import com.quintor.worqplace.domain.Reservation;
import com.quintor.worqplace.domain.Room;
import com.quintor.worqplace.domain.Workplace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservableRepository reservableRepository;


    public void getReservation() {
        Room room = new Room("Room 1");
        Workplace workplace = new Workplace("Workplace 1", room);
        reservableRepository.save(room);
        reservableRepository.save(workplace);
        Reservation reservation = new Reservation(workplace);
        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    public ReservationDTO getReservationById(Long id) throws ReservationNotFoundException {
        return new ReservationDTO(this.reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id)));
    }
}
