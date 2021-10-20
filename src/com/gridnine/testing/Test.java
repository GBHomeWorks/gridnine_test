package com.gridnine.testing;


import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Flight> flight = FlightBuilder.createFlights();
        FilterChain filterChain = new FilterChain(true, 2, 500, 250);
        filterChain.baseFilter(flight);

    }

}
