package com.example.sortingvisualizer.controllers;

import com.example.sortingvisualizer.payload.SortRequestMessage;
import com.example.sortingvisualizer.sort.AbstractSortStrategy;
import com.example.sortingvisualizer.sort.AlgorithmFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RequestSortController {

    private final AlgorithmFactory algorithmFactory;

    @Autowired
    public RequestSortController(AlgorithmFactory algorithmFactory) {
        this.algorithmFactory = algorithmFactory;
    }


    @MessageMapping("/sort")
    public void processRequest(SortRequestMessage message) throws Exception {
        AbstractSortStrategy engine = this.algorithmFactory.getSort(message.getSortType());
        if (engine != null){
            engine.sort(message.getData());
        }
    }
}
