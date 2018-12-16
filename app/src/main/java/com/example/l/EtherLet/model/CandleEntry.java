package com.example.l.EtherLet.model;

public class CandleEntry {
    private float high = 0.0F;
    private float low = 0.0F;
    private float close = 0.0F;
    private float open = 0.0F;

    public float getHigh() {
        return high;
    }
    public float getClose() {
        return close;
    }
    public float getLow() {
        return low;
    }

    public float getOpen() {
        return open;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setOpen(float open) {
        this.open = open;
    }
}

