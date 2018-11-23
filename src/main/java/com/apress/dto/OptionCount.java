package com.apress.dto;

/**
 * 05-07-18
 *
 * @author Tom
 */

// Data Transfer Objects (data comes from the repository
public class OptionCount {
    private Long optionId;
    private int count;

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OptionCount{" +
                "optionId=" + optionId +
                ", count=" + count +
                '}';
    }
}
