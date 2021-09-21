<template>
  <div class="message-skeleton animate__animated" :class="directionClass" @animationend="fireMessage">
    <div class="message-header" :class="colorScheme.base">
      <span class="badge normalized hover-effect pointer" :class="colorScheme.light"
            @click="setRecipient">{{ message.fromUser }}</span> <span class="timestamp">{{ timeString }}</span>
    </div>

    <div class="message-body" :class="colorScheme.light">
      {{ message.body }}
    </div>
  </div>
</template>

<script>
import "animate.css"

export default {
  name: "Message",
  props: {
    message: {
      type: Object,
      default() {
        return {
          direction: "",
          fromUser: "",
          toUser: "",
          fired: false,
          timestamp: 0,
          type: "",
          body: ""
        }
      }
    },
  },
  data() {
    return {}
  },
  computed: {
    directionClass() {
      return {
        'animate__fadeInRight': this.message.direction === 'OUTCOME' && !this.message.fired,
        'animate__fadeInLeft': this.message.direction === 'INCOME' && !this.message.fired,
        'income': this.message.direction === 'INCOME',
        'outcome': this.message.direction === 'OUTCOME'
      }
    },
    colorScheme() {
      return this.$store.state.configuration.colorScheme
    },
    timeString() {
      const date = new Date(this.message.timestamp);
      const year = date.getFullYear()
      const month = ("0" + date.getMonth()).slice(-2)
      const day = ("0" + date.getDay()).slice(-2)
      const dash = "-"
      const colon = ":"
      const hours = ("0" + date.getHours()).slice(-2)
      const minutes = ("0" + date.getMinutes()).slice(-2)

      return year + dash + month + dash + day + " / " + hours + colon + minutes
    }
  },
  methods: {
    fireMessage() {
      this.$store.commit("websocket/fireMessage")
    },
    setRecipient() {
      if (this.message.type !== "NOTIFICATION" && this.message.type !== "WELCOME") this.$store.commit("websocket/setRecipient", this.message.fromUser)
    }
  }
}
</script>

<style lang="scss" scoped>

.message-skeleton {
  position: relative;
  color: color-yiq($primary);
  width: 50%;
  max-width: 500px;
  @include media("phone", "tablet") {
    min-width: 200px;
  }
  margin-bottom: spacing(1);

  .message-header {
    display: flex;
    border-radius: spacing(1) spacing(1) 0 0;
    padding: spacing(1);
    background: $primary;
    justify-content: center;
    flex-direction: column;
  }

  .message-body {
    border-radius: 0 0 spacing(1) spacing(1);
    padding: spacing(2);
    background: $primary-light;
  }

  &.income {
    padding-left: spacing(3);

    .message-header {
      border-radius: 0 spacing(1) 0 0;

      &::before {
        content: '';
        display: block;
        position: absolute;
        left: spacing(1);
        top: 0;
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 16px 16px 0 !important;
        border-top-color: transparent !important;
        border-right-color: $primary;
        border-bottom-color: transparent !important;
        border-left-color: transparent !important;
      }
    }
  }

  &.outcome {
    padding-right: spacing(3);
    margin-left: auto;

    .message-header {
      border-radius: spacing(1) 0 0 0;
      color: color-yiq($dark-gray-300);

      &::after {
        content: '';
        display: block;
        position: absolute;
        right: spacing(1);
        top: 0;
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 0 16px 16px !important;
        border-top-color: transparent !important;
        border-right-color: transparent !important;
        border-bottom-color: transparent !important;
        border-left-color: $primary;
      }
    }
  }
}
</style>
