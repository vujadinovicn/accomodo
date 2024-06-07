package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

public class DestinationFlag {
    private Long destinationId;
    private double averageRating;
    private int count;
    private LocalDateTime loginTimestamp;

    public DestinationFlag(Long destinationId, double averageRating, int count, LocalDateTime loginTimestamp) {
        this.destinationId = destinationId;
        this.averageRating = averageRating;
        this.count = count;
        this.loginTimestamp = loginTimestamp;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDateTime getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(LocalDateTime loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    @Override
    public String toString() {
        return "DestinationFlag [destinationId=" + destinationId + ", averageRating=" + averageRating + ", count="
                + count + ", loginTimestamp=" + loginTimestamp + "]";
    }

}
