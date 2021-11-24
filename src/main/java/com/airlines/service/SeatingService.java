package com.airlines.service;

import com.airlines.model.Seat;
import com.airlines.util.Status;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.airlines.util.SeatType.*;
import static java.util.stream.Collectors.toList;

public class SeatingService {

    private int[][] seatConfiguration;
    private final Map<String, Seat> seatMap;
    private final int passengerCount;

    public SeatingService(final int[][] seatConfiguration, final int passengerCount) {

        this.seatConfiguration = seatConfiguration;
        this.seatMap = initializeSeatMap(seatConfiguration);
        this.passengerCount = passengerCount;
    }

    private Map<String, Seat> initializeSeatMap(final int[][] seatConfiguration) {

        final int columns = seatConfiguration.length;
        final Map<String, Seat> seatMap = new LinkedHashMap<>();

        AtomicInteger rows = new AtomicInteger(0);

        IntStream.range(0, columns).forEach(idx -> {

            int row = seatConfiguration[idx][1];
            rows.updateAndGet(val -> val > row ? val : row);
        });

        for (int rowIndex = 0; rowIndex < rows.intValue(); ++rowIndex) {

            for (int columnIndex = 0; columnIndex < columns; ++columnIndex) {

                if (rowIndex < seatConfiguration[columnIndex][1]) {

                    int cols = seatConfiguration[columnIndex][0];

                    for (int colIndex = 0; colIndex < cols; ++colIndex) {

                        Seat seat = new Seat();

                        if (columnIndex == 0) {

                            if (colIndex == 0) {
                                seat.setSeatType(WINDOW);
                            } else if (colIndex == cols - 1) {
                                seat.setSeatType(AISLE);
                            } else {
                                seat.setSeatType(CENTER);
                            }

                        } else if (columnIndex == columns - 1) {

                            if (colIndex == 0) {
                                seat.setSeatType(AISLE);
                            } else if (colIndex == cols - 1) {
                                seat.setSeatType(WINDOW);
                            } else {
                                seat.setSeatType(CENTER);
                            }

                        } else {

                            if (colIndex == 0 || colIndex == cols - 1) {
                                seat.setSeatType(AISLE);
                            } else {
                                seat.setSeatType(CENTER);
                            }
                        }

                        String key = new StringBuilder(String.valueOf(columnIndex))
                                .append("_")
                                .append(rowIndex)
                                .append("_")
                                .append(colIndex)
                                .toString();

                        seatMap.put(key, seat);
                    }
                }
            }
        }

        return seatMap;
    }

    public void allocateSeats() {

        if (!isPassengerCountValid()) {

            throw new RuntimeException("Invalid Passenger count");
        }

        List<Seat> seatsByPriority = groupSeatsByPriority();

        for (int count = 0; count < passengerCount; ++count) {

            Seat seat = seatsByPriority.get(count);
            seat.setPassengerNumber(count + 1);
            seat.setStatus(Status.BOOKED);
        }
    }

    private Boolean isPassengerCountValid() {

        AtomicInteger capacity = new AtomicInteger(0);
        IntStream.range(0, seatConfiguration.length).forEach(index -> {

            capacity.addAndGet(seatConfiguration[index][0] * seatConfiguration[index][1]);
        });

        return (this.passengerCount <= capacity.intValue());
    }

    private List<Seat> groupSeatsByPriority() {

        return seatMap.values()
                .stream()
                .sorted(Comparator.comparing(seat -> seat.getSeatType().getPriority()))
                .collect(toList());
    }

    public void printSeatAllocation() {

        final int columns = seatConfiguration.length;
        AtomicInteger rows = new AtomicInteger(0);

        IntStream.range(0, columns).forEach(idx -> {

            int row = seatConfiguration[idx][1];
            rows.updateAndGet(val -> val > row ? val : row);
        });

        List<String> headerKeys = seatMap.entrySet().stream()
                .filter(entry -> entry.getKey().matches("[0-9]_0_[0-9]"))
                .map(entry -> entry.getValue().getSeatType().getSeatTypeShortCode())
                .collect(Collectors.toList());

        headerKeys.forEach(header -> {

            System.out.print(String.format("%1s   ", header));
        });

        System.out.println();

        for (int rowIndex = 0; rowIndex < rows.intValue(); ++rowIndex) {

            for (int columnIndex = 0; columnIndex < columns; ++columnIndex) {

                if (rowIndex < seatConfiguration[columnIndex][1]) {

                    int cols = seatConfiguration[columnIndex][0];

                    for (int colIndex = 0; colIndex < cols; ++colIndex) {

                        String key = new StringBuilder(String.valueOf(columnIndex))
                                .append("_")
                                .append(rowIndex)
                                .append("_")
                                .append(colIndex)
                                .toString();

                        int passengerNumber = this.seatMap.get(key).getPassengerNumber();

                        System.out.print(passengerNumber == 0 ? "UA " : String.format("%02d", passengerNumber) + " ");
                    }
                } else {

                    System.out.print("\t\t");
                }

                System.out.print("\t");
            }

            System.out.println();
        }
    }
}