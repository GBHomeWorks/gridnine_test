package com.gridnine.testing;

public class FilterChain implements Filter{
    boolean fromNow;
    long gapTime;

    public void baseFilter() {}
    public void fromNowFilter() {}
    public void timeGapFilter() {}
}
