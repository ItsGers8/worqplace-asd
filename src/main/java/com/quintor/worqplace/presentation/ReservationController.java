package com.quintor.worqplace.presentation;

import com.quintor.worqplace.application.ReservationService;
import com.quintor.worqplace.application.dto.ReservationDTO;
import com.quintor.worqplace.application.exception.ReservationNotFoundException;
import com.quintor.worqplace.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getAllReservation() {
        return this.reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.reservationService.getReservationById(id), HttpStatus.OK);
        } catch (ReservationNotFoundException reservationNotFoundException) {
            return new ResponseEntity<>(reservationNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/start")
    public void startUp() {
        this.reservationService.getReservation();
    }
}
