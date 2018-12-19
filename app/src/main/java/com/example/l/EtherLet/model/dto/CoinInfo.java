package com.example.l.EtherLet.model.dto;

public class CoinInfo {
    private String symbol;
    private String name;
    private String priceUSD;
    private String priceCNY;
    private String high;
    private String low;
    private String volume;
    private String changeHour;
    private String changeDaily;
    private String changeWeekly;
    private String changeMonthly;

    public CoinInfo(){
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public String getPriceCNY() {
        return priceCNY;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getVolume() {
        return volume;
    }

    public String getChangeHour() {
        return changeHour;
    }

    public String getChangeDaily() {
        return changeDaily;
    }

    public String getChangeWeekly() {
        return changeWeekly;
    }

    public String getChangeMonthly() {
        return changeMonthly;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public void setPriceCNY(String priceCNY) {
        this.priceCNY = priceCNY;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setChangeHour(String changeHour) {
        this.changeHour = changeHour;
    }

    public void setChangeDaily(String changeDaily) {
        this.changeDaily = changeDaily;
    }

    public void setChangeMonthly(String changeMonthly) {
        this.changeMonthly = changeMonthly;
    }

    public void setChangeWeekly(String changeWeekly) {
        this.changeWeekly = changeWeekly;
    }

}
