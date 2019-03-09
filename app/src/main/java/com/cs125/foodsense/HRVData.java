package com.cs125.foodsense;

class HRVData {
    private int day;
    private int hrv;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHrv() {
        return hrv;
    }

    public void setHrv(int hrv) {
        this.hrv = hrv;
    }

    @Override
    public String toString() {
        return "HRVData{" +
                "day=" + day +
                ", hrv=" + hrv +
                '}';
    }
}
