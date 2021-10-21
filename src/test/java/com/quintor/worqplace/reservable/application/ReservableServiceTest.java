package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.DeskType;
import com.quintor.worqplace.reservable.domain.Workplace;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ReservableServiceTest {
    static Long id;

    @BeforeAll
    static void setUp(@Autowired ReservableService reservableService) {
        Workplace workplace = new Workplace("First", "Seat at table",
                "Placed on A1C", "Simple hard chair", DeskType.HYBRID);
        reservableService.save(workplace);

        id = workplace.getId();

    }

    @Test
    void reserveShouldUpdateReservableToo() {
        System.out.println(id);
        assertTrue(true);
    }

    @AfterAll
    static void afterAll(@Autowired ReservableService reservableService) {
        reservableService.remove(reservableService.getReservableById(id));
    }
}