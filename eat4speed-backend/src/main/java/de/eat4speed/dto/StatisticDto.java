package de.eat4speed.dto;

public class StatisticDto {

    public long timestamp;
    public double value;

    public StatisticDto() {
    }

    public StatisticDto(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
