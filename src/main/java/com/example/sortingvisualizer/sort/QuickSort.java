package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

public class QuickSort extends AbstractSortStrategy{
    private String LEFT_COLOR = "red";
    private String RIGHT_COLOR = "blue";
    private String PIVOT_COLOR = "green";
    public QuickSort(SimpMessagingTemplate template) {
        super(template);
    }
    private int getPivot(int[] data, int left, int right){
        int mid = (left + right) / 2;
        if (data[mid] < data[left]){
            onChangeColor(left, this.LEFT_COLOR);
            onChangeColor(mid, this.RIGHT_COLOR);
            swap(data, mid, left);
            onAfterSwap(mid, left);
        }
        if (data[right] < data[left]){
            onChangeColor(left, this.LEFT_COLOR);
            onChangeColor(right, this.RIGHT_COLOR);
            swap(data, right, left);
            onAfterSwap(right, left);
        }
        if (data[mid] < data[right]){
            onChangeColor(mid, this.LEFT_COLOR);
            onChangeColor(right, this.RIGHT_COLOR);
            swap(data, right, mid);
            onAfterSwap(right, mid);
        }
        return data[right];
    }
    private void quickSort(int[] data, int min, int max){
        if (min >= max) return;
//        int pivot = getPivot(data, min, max);
        int pivot = data[max];
        onChangeColor(max, this.PIVOT_COLOR);
        int right = max - 1, left = min;

        while (left <= right){
            onChangeColor(left, this.LEFT_COLOR);
            onChangeColor(right, this.RIGHT_COLOR);
            while (left <= right && data[left] < pivot) {
                onChangeColor(left, this.BASE_COLOR);
                left++;
                onChangeColor(left, this.LEFT_COLOR);
            };
            while (left <= right && data[right] > pivot) {
                onChangeColor(right, this.BASE_COLOR);
                right--;
                onChangeColor(right, this.RIGHT_COLOR);
            }
            if (left <= right){
                swap(data, left, right);
                onAfterSwap(left, right);
                left++;right--;
            }else {
                onChangeColor(left, this.BASE_COLOR);
                onChangeColor(right, this.BASE_COLOR);
            }
        }
        onChangeColor(left, this.LEFT_COLOR);
        onChangeColor(max, this.RIGHT_COLOR);
        swap(data, left, max);
        onAfterSwap(left, max);

        quickSort(data, min, left -1);
        quickSort(data, left +1, max);
    }
    @Override
    public void sort(int[] data) {
        quickSort(data, 0, data.length-1);
        onFinish();
        System.out.println(Arrays.toString(data));
    }
}
