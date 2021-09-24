import * as StompJs from "@stomp/stompjs";
import * as SockJS from "sockjs-client";

export default (ctx, inject) => {
  ctx.stompClient = new StompJs.Client({
    webSocketFactory() {
      return new SockJS('/gs-guide-websocket');
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
