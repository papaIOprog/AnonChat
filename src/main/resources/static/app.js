var stompClient = null;

var roomID = "123"
var wurl = "";
var sessionId = "";
var toUser = ""

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        var url = '';
        url = stompClient.ws._transport.url;
        wurl = url;
        url = url.replace(
            "ws://localhost:8080/gs-guide-websocket/", "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        console.log("Your current session is: " + url);
        sessionId = url;

        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/room/' + roomID, function (greeting) {
            showGreeting(JSON.parse(greeting.body).body);
        });
        stompClient.subscribe('/queue/room/' + roomID, function (greeting) {
            showGreeting(JSON.parse(greeting.body).body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/chat/" + roomID, {}, JSON.stringify({'body': $("#name").val()}));
}

function sendToName() {
    stompClient.send("/app/chat/" + roomID, {}, JSON.stringify({'toUser': toUser, 'body': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    $("#sendto").click(function () {
        sendToName();
    });
});
