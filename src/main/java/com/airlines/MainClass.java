package com.airlines;

import com.airlines.service.SeatingService;
import com.google.gson.Gson;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("Enter the seat configuration in 2D array format ex. [[3,4], [2,3], [2,2]]");
        Scanner scanner = new Scanner(System.in);
        String inputConfiguration = scanner.nextLine();
        int[][] seatConfiguration = null;

        try {

            final Gson gson = new Gson();
            seatConfiguration = gson.fromJson(inputConfiguration, int[][].class);

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Expected array in 2D format");
            scanner.close();
            return;
        }

        System.out.println("Enter the number of passengers");
        int passengerCount = scanner.nextInt();
        scanner.close();

        final SeatingService seatingService = new SeatingService(seatConfiguration, passengerCount);

        seatingService.allocateSeats();
        seatingService.printSeatAllocation();
    }
}
