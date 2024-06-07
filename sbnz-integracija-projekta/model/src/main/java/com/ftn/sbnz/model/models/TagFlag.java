package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

public class TagFlag {
    private Long tagId;
    private double averageRating;
    private int count;
    private LocalDateTime loginTimestamp;

    public TagFlag(Long tagId, double averageRating, int count, LocalDateTime loginTimestamp) {
        this.tagId = tagId;
        this.averageRating = averageRating;
        this.count = count;
        this.loginTimestamp = loginTimestamp;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
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
        return "TagFlag [tagId=" + tagId + ", averageRating=" + averageRating + ", count=" + count + ", loginTimestamp="
                + loginTimestamp + "]";
    }

}
