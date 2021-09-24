export const state = () => ({
  messages: [],
  userPrefix: "/user",
  queuePrefix: "/queue/room/",
  endpointPrefix: "/app/chat/",
  roomID: "main",
  connected: false,
  connecting: false,
  started: false,
  username: "",
  recipient: null
})

export const mutations = {
  init(state, client) {
    this.state.websocketClient = client
  },
  start(state) {
    state.started = true;
  },
  stop(state) {
    state.started = false;
    state.connecting = false;
    state.connected = false;
  },
  connected(state) {
    state.connected = true;
    state.connecting = false;
  },
  connecting(state) {
    state.connecting = true;
  },
  disconnected(state) {
    state.connected = false;
    state.connecting = false;
  },
  setRoomID(state, roomID) {
    state.roomID = roomID.trim().replace(" ", "").toLowerCase()
  },
  addMessage(state, message) {
    if (message.fromUser !== state.username) message.direction = "INCOME"
    else message.direction = "OUTCOME"
    state.messages.push(message)
  },
  fireMessage(state) {
    const message = state.messages.find(o => o.fired !== true);
    if (message != null) message.fired = true;
    if (state.messages.length >= 100) {
      state.messages.shift();
    }
  },
  setUsername(state, username) {
    state.username = username;
  },
  setRecipient(state, username) {
    state.recipient = username;
  },
  unsetRecipient(state) {
    state.recipient = null;
  },
  wipeMessages(state) {
    state.messages = []
  }
}

export const getters = {
  getUserPrefix(state) {
    return state.userPrefix
  },
  getQueuePrefix(state) {
    return state.queuePrefix
  },
  getRoomID(state) {
    return state.roomID
  },
  getStarted(state) {
    return state.started
  },
  getConnected(state) {
    return state.connected
  },
  getWebsocketClient(state) {
    return state.websocketClient
  },
  getEndpointPrefix(state) {
    return state.endpointPrefix
  },
  getRecipient(state) {
    return state.recipient
  }
}

export const actions = {
  init(context) {
    this.$stompClient.beforeConnect = function (frame) {
      context.commit("disconnected")
      context.commit("connecting")
    };
    this.$stompClient.onDisconnect = function (frame) {
      context.commit("disconnected")
    };
  },

  connect(context) {
    if (!context.getters.getStarted) {
      context.commit("start")
      const client = this.$stompClient;
      const userPrefix = context.getters.getUserPrefix;
      const queuePrefix = context.getters.getQueuePrefix;
      const roomID = context.getters.getRoomID;

      this.$stompClient.onConnect = function (frame) {
        context.commit("connected")
        client.subscribe(userPrefix + queuePrefix + roomID, function (message) {
          const messageObject = JSON.parse(message.body);

          if (messageObject.type === "WELCOME") context.commit("setUsername", messageObject.toUser)
          context.commit("addMessage", messageObject)
        });

        client.subscribe(queuePrefix + roomID, function (message) {
          const messageObject = JSON.parse(message.body);
          context.commit("addMessage", messageObject)
        });
      };

      this.$stompClient.activate()
    } else {
      this.$stompClient.deactivate();
      context.commit("stop")
      context.commit("wipeMessages")
    }
  },
  send(context, message) {
    if (context.getters.getStarted && context.getters.getConnected) {
      const endpointPrefix = context.getters.getEndpointPrefix;
      const roomID = context.getters.getRoomID;
      const messageObject = {'body': message};
      const recipient = context.getters.getRecipient;
      if (recipient != null) {
        messageObject.toUser = recipient;
        context.commit("unsetRecipient")
      }
      this.$stompClient.publish({
        destination: endpointPrefix + roomID,
        headers: {},
        body: JSON.stringify(messageObject)
      });
    }
  }
}
