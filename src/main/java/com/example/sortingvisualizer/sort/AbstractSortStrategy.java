package com.example.sortingvisualizer.sort;

import com.example.sortingvisualizer.interfaces.ISortingVisualizer;
import com.example.sortingvisualizer.payload.ColorChangeMessage;
import com.example.sortingvisualizer.payload.ExchangeRequestMessage;
import com.example.sortingvisualizer.payload.RenderListMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

public abstract class AbstractSortStrategy implements ISortingVisualizer {
    protected SimpMessagingTemplate template;
    protected String BASE_COLOR = "rebeccapurple";
    public AbstractSortStrategy(SimpMessagingTemplate template) {
        this.template = template;
    }



    private void send(String method, int first, int second){
        ExchangeRequestMessage exchangeRequestMessage = new ExchangeRequestMessage(first, second);
        if (this.template != null)
            this.template.convertAndSend("/callback/"+method, exchangeRequestMessage);
    }
    protected void onBeforeSwap(int first, int second){
        send("before", first, second);
    }
    protected void onAfterSwap(int first, int second){
        send("after", first, second);
    }
    protected void onSwap(int first, int second){
        send("swap", first, second);
    }
    protected void onChangeColor(int id, String color){
        ColorChangeMessage colorChangeMessage =  new ColorChangeMessage(id, color);
        if (this.template != null)
            this.template.convertAndSend("/callback/changeBg", colorChangeMessage);
    }
    protected void onRenderList(int[] data){
        RenderListMessage renderListMessage =  new RenderListMessage(data);
        if (this.template != null)
            this.template.convertAndSend("/callback/renderList", renderListMessage);
    }
    protected void onFinish(){
        if (this.template != null)
             this.template.convertAndSend("/callback/end", "");
    }
    protected void swap(int[] data, int first, int second){

        onSwap(first, second);
        int temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }
}
