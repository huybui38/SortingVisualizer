package com.example.sortingvisualizer.payload;

public class SortRequestMessage {
    private int sortType;
    private int delay;
    private int[] data;

    public SortRequestMessage(int sortType, int[] data) {
        this.sortType = sortType;
        this.data = data;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}
