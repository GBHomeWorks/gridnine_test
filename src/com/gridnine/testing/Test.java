package com.gridnine.testing;


import java.util.List;

public class Test {
    public static void main(String[] args) {
        boolean fromNow = true;
        long gapTime = 2;
        int segmentsArrCapacity = 500;
        int flightsArrCapacity = 250;

        List<Flight> flight = FlightBuilder.createFlights();
        FilterChain filterChain = new FilterChain(fromNow, gapTime, segmentsArrCapacity, flightsArrCapacity);
        filterChain.baseFilter(flight);

    }

}
