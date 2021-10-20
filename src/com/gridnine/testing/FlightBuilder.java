package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class FlightBuilder {
    static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
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

//        System.out.println(segments.toString());
        return new Flight(segments);
    }
}
