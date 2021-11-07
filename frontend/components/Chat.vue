<template>
  <div class="container" :class="colorScheme.base">
    <div class="chat-header" :class="colorScheme.base">
      <div class="chat-header-button" :class="colorScheme.dark"><span class="badge round"
                                                                      :class="onlineIndicatorClass"></span></div>
      <div class="chat-header-button hover-effect pointer" :class="colorScheme.dark" @click="switchConnect()"><i
        class="gg-log-off"></i></div>
      <NuxtLink class="chat-header-button hover-effect pointer" :class="colorScheme.dark" to="/settings/"><i
        class="gg-brush"></i></NuxtLink>
      <div v-if="!stickyChat" class="chat-header-button hover-effect pointer" :class="colorScheme.dark"
           @click="turnStickyChat"><i class="gg-arrow-down"></i></div>
      <div v-if="stickyChat" class="chat-header-button" :class="colorScheme.dark"><i class="gg-arrow-down"></i></div>
    </div>
    <MessagesContainer></MessagesContainer>
    <div class="chat-footer" :class="colorScheme.dark">
      {{ recipientString }}
    </div>
    <MessageInputPanel></MessageInputPanel>
  </div>
</template>

<script>

export default {
  name: "Chat",
  data() {
    return {
      colorScheme: this.$store.state.runtime.colorScheme,
      counter: 0,
    }
  },
  computed: {
    color() {
      return this.$store.state.configuration.selectedColor
    },
    stickyChat() {
      return this.$store.state.configuration.stickyChat
    },
    connected() {
      return this.$store.state.websocket.connected
    },
    connecting() {
      return this.$store.state.websocket.connecting
    },
    onlineIndicatorClass() {
      return {
        "bg-color-yellow": this.connecting,
        "bg-color-green": !this.connecting && this.connected,
        "bg-color-red": !this.connecting && !this.connected
      }
    },
    recipientString() {
      const recipient = this.$store.state.websocket.recipient;
      return recipient != null ? "To: " + this.$userNameGenerator.getName(recipient) : ""
    }
  },
  watch: {
    color() {
      this.colorScheme = this.$store.getters["configuration/getColorScheme"]
    }
  },
  mounted() {
    this.colorScheme = this.$store.getters["configuration/getColorScheme"]
  },
  methods: {
    outcome() {
      this.$store.commit("runtime/addMessage", {'direction': 'OUTCOME', 'id': this.counter, "fired": false})
      this.counter++
    },
    income() {
      this.$store.commit("runtime/addMessage", {'direction': 'INCOME', 'id': this.counter, "fired": false})
      this.counter++
    },
    changeColor() {
      this.$store.commit("configuration/changeColor")
    },
    turnStickyChat() {
      this.$store.commit("runtime/stick")
    },
    switchConnect() {
      this.$store.dispatch("websocket/switchConnect")
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-flow: column;
  height: 100%;

  .chat-header {
    display: flex;
    justify-content: space-between;
    flex-basis: 0;
    padding-top: spacing(2);


    .chat-header-button {
      flex-basis: 0;
      flex-grow: 1;
      height: 48px;
      display: flex;
      justify-content: center;
      align-items: center;

      &:first-child {
        border-radius: spacing(1) 0 0 0;
      }

      &:last-child {
        border-radius: 0 spacing(1) 0 0;
      }
    }
  }

  .chat-footer {
    padding: spacing(1);
    font-style: italic;
  }
}
</style>
