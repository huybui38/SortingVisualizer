package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BubbleSort extends AbstractSortStrategy {
    public BubbleSort(SimpMessagingTemplate template){
        super(template);
    }

    @Override
    public void sort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                onBeforeSwap(j, j +1);
                if (data[j] > data[j + 1]){
                    swap(data, j, j+1);
                }
                onAfterSwap(j,j + 1);
            }
        }
        onFinish();
    }
}
