package com.example.sortingvisualizer.payload;

public class RenderListMessage {
    private int[] data;

    public RenderListMessage(int[] data) {
        this.data = data;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}
