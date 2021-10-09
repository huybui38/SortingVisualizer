package com.example.sortingvisualizer;

import com.example.sortingvisualizer.sort.*;
import com.sun.scenario.effect.Merge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SortingVisualizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortingVisualizerApplication.class, args);

        int[] data = new int[]{1,3, 0, 9,2, 10, 4};
        AbstractSortStrategy sorting = new MergeSort(null);
        sorting.sort(data);

    }

}
