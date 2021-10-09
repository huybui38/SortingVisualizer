package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

public class InsertionSort extends AbstractSortStrategy{
    public InsertionSort(SimpMessagingTemplate template) {
        super(template);
    }

    @Override
    public void sort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int current = data[i];
            int j = i -1;
            while (j >= 0){
                onBeforeSwap(j, j+1);
                if (data[j] > current){
                    onSwap(j, j+1);
                    data[j + 1] = data[j];
                    j--;
                }else {
                    onAfterSwap(j, j+1);
                    break;
                }
                onAfterSwap(j + 1, j+2);
            }
            data[j + 1] = current;
        }
        onFinish();
        System.out.println(Arrays.toString(data));
    }
}
