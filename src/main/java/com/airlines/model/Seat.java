package com.airlines.model;

import com.airlines.util.SeatType;
import com.airlines.util.Status;

public class Seat {

    private SeatType seatType;
    private Status status;
    private int passengerNumber;

    public Seat() {
        this.status = Status.VACANT;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatType=" + seatType +
                ", status=" + status +
                ", passengerNumber=" + passengerNumber +
                '}';
    }
}
