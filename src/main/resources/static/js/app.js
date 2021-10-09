
/// ~~~~~~~ DATA CREATION  ~~~~~~~ ///
function generateList(length){
    let data = [];
    for (let index = 0; index < length; index++) {
        data[index] = index + 1;
    }
    shuffle(data);
    return data;
}
function generateSpecialList(length){
    let data = [];
    for (let index = 0; index < length; index++) {
        data[index] = index + 1;
    }
    shuffle(data, 0, 2);
    return data;
}
function shuffle(array, from = 0, to = -1){
    let currentIndex = to == -1 ? array.length - 1 : to;
    let randomIndex, temp;
    let end = from - 1;
    while (currentIndex !== end){
        randomIndex = Math.floor(Math.random() * currentIndex);
        if (randomIndex !== currentIndex){
            temp = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temp;
        }
        currentIndex--;
    }
}
/// ~~~~~~~ RENDER LIST  ~~~~~~~ ///
function renderData(list){
    $("#list").empty();
    let ratio = 100 / list.length;
    for (let i = 0; i < list.length; i++) {
        let ele = `<div class="flex-grow-1 item align-self-end" id="item-${i}" style="height: ${ratio * (list[i])}%">${list.length > 25 ? '' : list[i]}</div>`;  
        $("#list").append(ele);
    }
}
/// ~~~~~ SWAPPING ~~~~~~ ///
async function beforeSwap(firstIndex, secondIndex){
    await changeBgColor(firstIndex, 'red');
    await changeBgColor(secondIndex, 'red');
}
async function afterSwap(firstIndex, secondIndex){
    await changeBgColor(firstIndex, 'rebeccapurple');
    await changeBgColor(secondIndex, 'rebeccapurple');
}

async function changeBgColor(elementID, color){
    return new Promise((resolve, reject) => { 
    setTimeout(() => {
        $(`#item-${elementID}`).css('backgroundColor', color);
        resolve();
    }, window.timeout);
    });
}
async function swap(firstIndex, secondIndex){
    return new Promise((resolve, reject) => { 
        setTimeout(() => {
            let tempHeight = $(`#item-${firstIndex}`).height();
            $(`#item-${firstIndex}`).height($(`#item-${secondIndex}`).height());    
            $(`#item-${secondIndex}`).height(tempHeight)
        
            //swap 2 ids
            $(`#item-${firstIndex}`).prop('id', 'firstTempId');
            $(`#item-${secondIndex}`).prop('id', 'secondTempId');
            $(`#firstTempId`).prop('id', `item-${firstIndex}`);
            $(`#secondTempId`).prop('id', `item-${secondIndex}`);
        
            let valTemp = $(`#item-${firstIndex}`).html();
            if (valTemp != '' && valTemp != undefined && valTemp != null) {
                $(`#item-${firstIndex}`).html($(`#item-${secondIndex}`).html());
                $(`#item-${secondIndex}`).html(valTemp);
            }
            resolve();
        }, window.timeout);
    });
  
}


/// ~~~~~~ handle UI request ~~~~~ ///
function generateRandomData(){
    let data = generateList($("#txtCount").val());
    window.data = data;
    renderData(data);
    $("#btnSort").attr("disabled", false);
}
function generateSpecialData(){
    if ($("#txtCount").val() <= 3){
        alert("Ít nhất 4 phần tử!");
        return;
    }
    let data = generateSpecialList($("#txtCount").val());
    window.data = data;
    renderData(data);
    $("#btnSort").attr("disabled", false);
}
function handleRangeCountChange(e){
    $("#txtCount").val( e.currentTarget.value);
}
function handleTxtCountChange(e){
    $("#countRange").val(e.currentTarget.value);
}
function handleDelayChange(e){
    window.timeout = e.currentTarget.value;
    $("#delayRange").val(e.currentTarget.value);
}
function handleRangeDelayChange(e){
    window.timeout = e.currentTarget.value;
    $("#txtDelay").val(e.currentTarget.value);
}
async function handleSort(){
    let sortType  = $("#sortType").val();
    let delay = $("#txtDelay").val();
   
    // window.timeout = 0;
    if (sortType == -1)
        return alert("Chọn thuật toán!");
    if (window.data == null){
        generateRandomData($("#txtCount").val());
    }
    stompClient.send("/app/sort", {}, JSON.stringify({
        'sortType': sortType,
        'data':window.data,
        'delay':delay
        }));
  
    $("#btnSort").attr("disabled", true);
}
/// ~~~~~ binding ~~~~~~ ///
$("#generator").on("click", generateRandomData);
$("#specialGenerator").on("click", generateSpecialData);
$("#countRange").on("change", handleRangeCountChange);
$("#delayRange").on("change", handleRangeDelayChange);
$("#txtCount").on("change", handleTxtCountChange);
$("#txtDelay").on("change", handleDelayChange);
$("#btnSort").on("click", handleSort);
/// ~~~~~~~ MAIN  ~~~~~~~ ///

var socket = new SockJS('/hello');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    // setConnected(true);
    console.log('Connected: ' + frame);
    let queue = [];
    stompClient.subscribe('/callback/swap', async function (data) {
        const obj = JSON.parse(data.body);
        queue.push({func:swap, obj});
    });
    stompClient.subscribe('/callback/before', async function (greeting) {
        const obj = JSON.parse(greeting.body);
        queue.push({func:beforeSwap, obj});
    });
    stompClient.subscribe('/callback/after', async function (greeting) {
        const obj = JSON.parse(greeting.body);
        queue.push({func:afterSwap, obj});
    });
    stompClient.subscribe('/callback/changeBg', async function (greeting) {
        const obj = JSON.parse(greeting.body);
        obj.type = 'bg';
        queue.push({func:changeBgColor, obj});
    });
    stompClient.subscribe('/callback/renderList', async function (greeting) {
        const obj = JSON.parse(greeting.body);
        obj.type = 'render';
        queue.push({func:renderData, obj});
    });
    stompClient.subscribe('/callback/end', async function () {
        while (queue.length !== 0){
            let firstObj = queue.shift();
            if (firstObj.obj.type &&firstObj.obj.type == 'bg'){
                let {id, color} = firstObj.obj;
                await firstObj.func(id, color);
            }else if (firstObj.obj.type &&firstObj.obj.type == 'render'){
                let {data} = firstObj.obj; 
                await firstObj.func(data);
            }
            else await firstObj.func(firstObj.obj.first, firstObj.obj.second);
        }
    });
});
$(document).ready(() => {
    $("#txtCount").val($("#countRange").val());
    $("#txtDelay").val($("#delayRange").val());
    window.timeout = $("#delayRange").val();
})