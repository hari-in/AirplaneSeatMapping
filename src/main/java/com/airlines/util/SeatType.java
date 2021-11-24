package com.airlines.util;

public enum SeatType {

    WINDOW("W", 2), CENTER("C", 3), AISLE("A", 1);

    private String seatTypeShortCode;
    private Integer priority;

    SeatType(final String seatTypeShortCode, final Integer priority) {
        this.seatTypeShortCode = seatTypeShortCode;
        this.priority = priority;
    }

    public String getSeatTypeShortCode() {
        return this.seatTypeShortCode;
    }

    public Integer getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return seatTypeShortCode;
    }
}
