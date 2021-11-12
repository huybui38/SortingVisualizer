package com.example.sortingvisualizer.sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmFactory {
    private SimpMessagingTemplate template;

    @Autowired
    public AlgorithmFactory(SimpMessagingTemplate template) {
        this.template = template;
    }

    public AbstractSortStrategy getSort(int type){
        switch (type){
            case 1:
                 return new SelectionSort(this.template);
            case 2:
                return new BubbleSort(this.template);
            case 3:
                return new InsertionSort(this.template);
            case 4:
                return new QuickSort(this.template);
            case 5:
                return new MergeSort(this.template);
            case 6:
                return new OptimizedBubbleSort(this.template);
            case 7:
                return new HeapSort(this.template);
        }
        return null;
    }
}
