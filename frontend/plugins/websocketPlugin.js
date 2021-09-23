import * as StompJs from "@stomp/stompjs";
import * as SockJS from "sockjs-client";

export default (ctx, inject) => {
  ctx.stompClient = new StompJs.Client({
    webSocketFactory() {
      return new SockJS('http://localhost:8080/gs-guide-websocket');
    },
    debug(str) {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });
  inject('stompClient', ctx.stompClient)
}
