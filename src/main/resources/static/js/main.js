
'use strict';
var hang_id = document.querySelector('#hang_id').value;

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');

var usernameForm = document.querySelector('#usernameForm');

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');

var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = (document.querySelector('#name')).value.trim();
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);


    }
    event.preventDefault();
}
function onConnected() {
    // Subscribe to the  room#hang_id
    stompClient.subscribe('/room/'+hang_id, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser/"+hang_id,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';

}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {

        var now = new Date();
        var year = now.getFullYear();
        var month = now.getMonth()+1<10 ? '0'+(now.getMonth()+1) : now.getMonth()+1;
        var date = now.getDate()<10 ? '0'+now.getDate() : now.getDate();
        var hour = now.getHours()<10 ? '0'+now.getHours() : now.getHours() ;
        var minute = now.getMinutes()<10 ? '0'+now.getMinutes() : now.getMinutes();
        var second = now.getSeconds()<10 ? '0'+now.getSeconds() : now.getSeconds() ;
        var time = year + '-' + month + '-' + date + " " + hour + ':' + minute + ':' + second;



        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            hang_id : hang_id,
            time : time
        };


        const url = '/api/hangout/chatting';
        fetch(url, {
            method : "post",
            body : JSON.stringify(chatMessage), // 자바스크립트 객체를 json 문자열로 변환해준다.
            headers : {"Content-Type" : "application/json"}
        }).then(response => {
            stompClient.send("/app/chat.sendMessage/" + hang_id , {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        });
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log("♥" + payload.body);
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        usernameElement.classList.add("sender");
        /***************************** 전송 시간 표시하려고 함!!!!!!!! *********************************/
        var sendtimeElement = document.createElement('span');
        var sendtimeText = document.createTextNode(message.time);
        sendtimeElement.appendChild(sendtimeText);
        messageElement.appendChild(sendtimeElement);
        sendtimeElement.classList.add("send_time");
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);



    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);