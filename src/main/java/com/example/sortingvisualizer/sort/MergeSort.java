package com.example.sortingvisualizer.sort;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

public class MergeSort extends AbstractSortStrategy{
    public MergeSort(SimpMessagingTemplate template) {
        super(template);
    }
    private void merge(int[] data, int left, int mid, int right){
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        // L ... left to mid
        // R ... mid + 1 to right
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = data[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = data[mid + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
            int leftMin = left + i, rightMin = mid + j + 1;
            onBeforeSwap(leftMin, rightMin);
            if (L[i] <= R[j]) {
                data[k] = L[i];
                i++;
            }
            else {
                data[k] = R[j];
                j++;
            }
            onAfterSwap(leftMin, rightMin);
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            data[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            data[k] = R[j];
            j++;
            k++;
        }
        onRenderList(data);
    }
    private void mergeSort(int[] data, int left, int right){
        if (left < right) {
            // Find the middle point
            int mid = left + (right-left)/2;

            // Sort first and second halves
            mergeSort(data, left, mid);
            mergeSort(data, mid + 1, right);

            // Merge the sorted halves
            merge(data, left, mid, right);
        }
    }
    @Override
    public void sort(int[] data) {
        mergeSort(data, 0, data.length - 1);
        onFinish();
        System.out.println(Arrays.toString(data));
    }
}
