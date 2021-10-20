package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class FilterChain implements Filter {
    static boolean fromNow;
    static long gapTime;
    int segmentsArrCapacity;
    int flightsArrCapacity;

    FilterChain(boolean fromNow, long gapTime, int segmentsArrCapacity, int flightsArrCapacity){
        this.fromNow = fromNow;
        this.gapTime = gapTime;
        this.segmentsArrCapacity = segmentsArrCapacity;
        this.flightsArrCapacity = flightsArrCapacity;
    }
    public static void baseFilter(List<Flight> fls) {
        List<Flight> flights = new ArrayList<>(500);

        for (int i = 0; i < fls.size(); i++) {
            List<Segment> segments = new ArrayList<>(250);
            fls.get(i).getSegments().parallelStream().
                    distinct().
                    filter(arr -> arr.getArrivalDate() != null).
                    filter(dep -> dep.getDepartureDate() != null).
                    filter(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())).
                    forEach(segment -> segments.add(segment));
            flights.add(new Flight(segments));
        }
        System.out.println(flights);
    }

    public void fromNowFilter() {
    }

    public void timeGapFilter() {
    }

}
//        List<Segment> seg = segments.parallelStream().distinct().
//                filter(arr -> arr.getArrivalDate() != null).
//                filter(dep -> dep.getDepartureDate() != null).
//                filter(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())).
//                reduce(segments, (segment, segment2) ->
//                {if ((segment.getArrivalDate().isAfter(segment2.getDepartureDate().minusHours(2))) ||
//                        (segment.getArrivalDate().isEqual(segment2.getDepartureDate().minusHours(2))))
//                    return segment2;
//                });