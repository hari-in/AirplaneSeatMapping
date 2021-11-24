package com.airlines.service.test;

import com.airlines.service.SeatingService;
import com.google.gson.Gson;
import org.junit.Test;

public class SeatingServiceTest {

    @Test(expected = RuntimeException.class)
    public void testPassengerOverflow() {

        String inputSeatingConfiguration = "[[3,2],[4,3],[2,3],[3,4]]";
        Gson gson = new Gson();
        int[][] seatConfiguration = gson.fromJson(inputSeatingConfiguration, int[][].class);
        int passengerCount = 60;

        SeatingService seatingService = new SeatingService(seatConfiguration, passengerCount);
        seatingService.allocateSeats();
    }

    @Test
    public void allocateSeatsAndDisplay() {

        String inputSeatingConfiguration = "[[3,2],[4,3],[2,3],[3,4]]";
        Gson gson = new Gson();
        int[][] seatConfiguration = gson.fromJson(inputSeatingConfiguration, int[][].class);
        int passengerCount = 30;

        SeatingService seatingService = new SeatingService(seatConfiguration, passengerCount);
        seatingService.allocateSeats();
        seatingService.printSeatAllocation();
    }
}
