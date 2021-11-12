package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

public class HeapSort extends AbstractSortStrategy{
    public HeapSort(SimpMessagingTemplate template) {
        super(template);
    }

    @Override
    public void sort(int[] data) {

        int n = data.length;

        for (int i = n/2 -1; i>=0; i--){
            heapify(data, n, i);
        }

        for (int i = n-1; i>=0; i--){
            onBeforeSwap(0, i);
            swap(data, 0, i);
            onAfterSwap(0, i);
            heapify(data, i, 0);
        }

        onFinish();
        System.out.println(Arrays.toString(data));
    }
    private void heapify(int[] data, int heapSize, int i){
        int largest = i;
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        if (left < heapSize && data[left] > data[largest]){
            largest =  left;
        }
        if (right <heapSize && data[right] > data[largest]){
            largest = right;
        }
        if (largest != i){
            onBeforeSwap(largest, i);
            swap(data, largest, i);
            onAfterSwap(largest, i);
            heapify(data, heapSize, largest);
        }
    }
}
