<template>
  <div class="message-container-wrapper border-colored scrollable-y" :class="colorScheme.dark" @scroll="handleScrollUp">
    <div class="message-container">
      <div class="message-container-filler">

      </div>
      <Message v-for="message in messages" :key="message.timestamp" :fired="message.fired" :message="message"></Message>
    </div>
  </div>
</template>

<script>
export default {
  name: "MessagesContainer",
  data() {
    return {
      colorScheme: this.$store.state.runtime.colorScheme,
    }
  },
  computed: {
    color() {
      return this.$store.state.configuration.selectedColor
    },
    messages() {
      return this.$store.state.websocket.messages
    },
    stickyChat() {
      return this.$store.state.runtime.stickyChat
    },
  },
  watch: {
    color() {
      this.colorScheme = this.$store.getters["configuration/getColorScheme"]
    },
    stickyChat() {
      if (this.stickyChat) {
        this.scrollDown();
      }
    }
  },
  mounted() {
    this.colorScheme = this.$store.getters["configuration/getColorScheme"]
  },
  updated() {
    if (this.stickyChat) {
      this.scrollDown();
    }
  },
  methods: {
    scrollDown() {
      this.$el.scrollTop = this.$el.scrollHeight;
    },
    unstick() {
      this.$store.commit("runtime/unstick");
    },
    stick() {
      this.$store.commit("runtime/stick");
    },
    handleScrollUp() {
      const el = this.$el;
      if (el.scrollTop < el.scrollHeight - 1400) {
        this.unstick()
      }
    }
  },
}
</script>

<style lang="scss" scoped>
.message-container-wrapper {
  position: relative;
  border-width: spacing(1) !important;
  flex: 1 1 auto;
  overflow-x: hidden;

  .message-container {
    display: flex;
    flex-flow: column;
    border-width: spacing(1) !important;
    border-radius: spacing(1);
    height: 100%;

    .message-container-filler {
      flex: 1 1 auto;
    }
  }
}
</style>
