package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SelectionSort extends AbstractSortStrategy {
    public SelectionSort(SimpMessagingTemplate template) {
        super(template);
    }

    @Override
    public void sort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = i+1; j < data.length; j++) {
                onBeforeSwap(i, j);
                if (data[i] > data[j])
                    swap(data, i, j);
                onAfterSwap(i, j);
            }
        }
        onFinish();
    }
}
