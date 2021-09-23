<template>
  <div class="message-input-panel">
    <div class="message-input-area-wrapper" :class="colorScheme.light">
      <textarea v-model="messagePayload" class="message-input-area" :class="colorScheme.light"
                onInput="this.parentNode.dataset.replicatedValue = this.value"
                @keydown="handleEvent($event)"></textarea>
    </div>
    <div class="message-input-buttons">
      <div class="message-input-button hover-effect pointer" :class="colorScheme.dark" @click="handleEvent($event)">
        <i class="gg-arrow-right"></i>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "TextareaPanel",
  data() {
    return {
      colorScheme: this.$store.state.runtime.colorScheme,
      messagePayload: ""
    }
  },
  computed: {
    color() {
      return this.$store.state.configuration.selectedColor
    },
    connected() {
      return this.$store.state.websocket.connected
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
    handleEvent(event) {
      const keyCode = event.which || event.keyCode;
      if (this.connected) {
        if (event.type === "keydown" && keyCode === 13 && !event.shiftKey) {
          event.preventDefault();
          if ((event.repeat == null || event.repeat === false) && this.validateMessage()) {
            this.send();
          }
        } else if (event.type === "click" && this.validateMessage()) {
          this.send()
        }
      }
    },
    validateMessage() {
      return this.messagePayload.replace("\n", '')
        .replace(' ', '')
        .length > 0
    },
    send() {
      this.$store.dispatch("websocket/send", this.messagePayload);
      this.messagePayload = ""
    }
  },
}
</script>

<style lang="scss" scoped>
.message-input-panel {
  display: flex;
  flex-direction: row;
  margin-bottom: spacing(2);

  .message-input-area-wrapper {
    padding: spacing(1);
    border-radius: 0 0 0 spacing(1);
    max-height: 180px;
    min-height: spacing(8);
    width: 100%;
    overflow-y: scroll;
    display: grid;

    &::after {
      content: attr(data-replicated-value) " ";
      white-space: pre-wrap;
      visibility: hidden;
    }

    & > .message-input-area {
      resize: none;
      overflow: hidden;
    }

    & > .message-input-area,
    &::after {
      border: 1px solid black;
      padding: 0.5rem;
      font: inherit;
      grid-area: 1 / 1 / 2 / 2;
    }

    .message-input-area {
      border: none;
      overflow: visible;

      &:focus {
        outline: none;
      }
    }

    &:focus {
      outline: none;
    }
  }

  .message-input-buttons {
    display: flex;
    flex-direction: column;
    width: spacing(6);

    .message-input-button {
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;

      border-radius: 0 0 spacing(1) 0;
    }
  }
}

</style>
