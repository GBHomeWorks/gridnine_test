package com.gridnine.testing;

public interface Filter {
    default void baseFilter() {}
    default void fromNowFilter() {}
    default void timeGapFilter() {}
}
