package com.gridnine.testing;

//import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import java.util.Arrays;


public class FilterChain implements Filter {
    static boolean fromNow;
    static long gapTime;
    static int segmentsArrCapacity;
    static int flightsArrCapacity;

    FilterChain(boolean fromNow, long gapTime, int segmentsArrCapacity, int flightsArrCapacity) {
        this.fromNow = fromNow;
        this.gapTime = gapTime;
        this.segmentsArrCapacity = segmentsArrCapacity;
        this.flightsArrCapacity = flightsArrCapacity;
    }

    public static void baseFilter(List<Flight> fls) {
        List<Flight> flights = new ArrayList<>(flightsArrCapacity);

        for (int i = 0; i < fls.size(); i++) {
            List<Segment> segments = new ArrayList<>(segmentsArrCapacity);
            fls.get(i).getSegments().parallelStream().
                    distinct().
                    filter(arr -> arr.getArrivalDate() != null).
                    filter(dep -> dep.getDepartureDate() != null).
                    filter(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())).
                    forEach(segment -> segments.add(segment));
            flights.add(new Flight(segments));
        }
        System.out.println("by baseFilter method:");
        System.out.println(flights);
        System.out.println("----------------------------------------------------------");
        fromNowFilter(flights);
    }

    public static void fromNowFilter(List<Flight> fls) {
        if (fromNow) {
            List<Flight> flights = new ArrayList<>(flightsArrCapacity);

            for (int i = 0; i < fls.size(); i++) {
                List<Segment> segments = new ArrayList<>(segmentsArrCapacity);
                fls.get(i).getSegments().parallelStream().
                        filter(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())).
                        forEach(segment -> segments.add(segment));
                flights.add(new Flight(segments));
            }
            System.out.println("by fromNowFilter method:");
            System.out.println(flights);
            System.out.println("----------------------------------------------------------");
            gapTimeFilter(flights);
        }
    }

    public static void gapTimeFilter(List<Flight> fls) {
        if (gapTime >= 0) {
            List<Flight> flights = new ArrayList<>(flightsArrCapacity);

            for (int i = 0; i < fls.size(); i++) {
                List<Segment> segments = new ArrayList<>(segmentsArrCapacity);
//  не удалось применить reduce
//                segs.add(fls.get(i).getSegments().parallelStream().reduce((segment, segment2) -> {
//                    if ((segment.getArrivalDate().isAfter(segment2.getDepartureDate().minusHours(gapTime))) ||
//                            (segment.getArrivalDate().isEqual(segment2.getDepartureDate().minusHours(gapTime))))
//                        return segment;
//                }));
//  не удалось применить reduce
//                segments.add(fls.get(i).getSegments().parallelStream().reduce(null, (segment, segment2) -> {
//                    if ((segment.getArrivalDate().isAfter(segment2.getDepartureDate().minusHours(gapTime))) ||
//                            (segment.getArrivalDate().isEqual(segment2.getDepartureDate().minusHours(gapTime))))
//                        return new Segment(segment2.getDepartureDate(), segment2.getArrivalDate());
//                }));

                for (int j = 0; j < fls.get(i).getSegments().size() -1; j++){
                    if(fls.get(i).getSegments().get(j).getArrivalDate().isAfter(fls.get(i).getSegments().get(j + 1).getDepartureDate().minusHours(gapTime)) ||
                            fls.get(i).getSegments().get(j).getArrivalDate().isEqual(fls.get(i).getSegments().get(j + 1).getDepartureDate().minusHours(gapTime)) ){
                        segments.add(fls.get(i).getSegments().get(j));
                    }
                }

                flights.add(new Flight(segments));
            }
            System.out.println("by gapTimeFilter method:");
            System.out.println(flights);
            System.out.println("----------------------------------------------------------");
        }
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