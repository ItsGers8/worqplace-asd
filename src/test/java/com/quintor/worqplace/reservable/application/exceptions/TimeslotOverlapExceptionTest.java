package com.quintor.worqplace.reservable.application.exceptions;

import com.quintor.worqplace.reservable.domain.DeskType;
import com.quintor.worqplace.reservable.domain.Room;
import com.quintor.worqplace.reservable.domain.Workplace;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeslotOverlapExceptionTest {
    private Timeslot timeslotAfternoon;
    private Timeslot timeslotMorning;
    private Room room;
    private Workplace workplace;

    @BeforeEach
    void setUp() throws InvalidTimeslotException, TimeslotOverlapException {
        this.timeslotAfternoon = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(13, 0), LocalTime.of(15, 0));
        this.timeslotMorning = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(10, 0), LocalTime.of(12, 0));
        this.room = new Room("First", "Big room",
                "This room is on the first floor on A1C", true, false);
        this.workplace = new Workplace("First", "Workplace in room",
                "Lovely place located on A1C",
                "Simple black chair",
                DeskType.HYBRID);
        this.room.addWorkplace(workplace);
        this.workplace.setRoom(room);
        this.workplace.reserve(this.timeslotAfternoon, false);
        this.room.reserve(this.timeslotMorning, false);
    }

    @Test
    @DisplayName("when workplace in room is not available this exception should be thrown")
    void workplaceInRoomNotAvailable() {
        assertThrows(TimeslotOverlapException.class, () -> this.room.reserve(this.timeslotAfternoon, false));
    }

    @Test
    @DisplayName("when room is not available this exception should be thrown")
    void roomNotAvailable() {
        assertThrows(TimeslotOverlapException.class, () -> this.room.reserve(this.timeslotMorning, false));
    }

    @Test
    @DisplayName("when workplace is not available this exception should be thrown")
    void workplaceNotAvailable() {
        assertThrows(TimeslotOverlapException.class, () -> this.workplace.reserve(this.timeslotAfternoon, false));
    }

    @Test
    @DisplayName("when room of workplace is not available this exception should be thrown")
    void roomOfWorkplaceNotAvailable() {
        assertThrows(TimeslotOverlapException.class, () -> this.workplace.reserve(this.timeslotMorning, false));
    }
}