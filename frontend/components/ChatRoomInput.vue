<template>
  <div class="chat-room-input-skeleton">
    <div class="chat-room-input-wrapper" :class="colorScheme.light">
      <input v-model="roomIDInput" :pattern="pattern.toString()" :disabled="started" class="chat-room-input"
             type="text">
    </div>
    <button v-if="!started" class="chat-room-input-button hover-effect pointer" :disabled="!valid"
            :class="colorScheme.dark" @click="applyRoomId">
      <div class="input-button-icons" :class="validClassObject">
        <i class="icon transited gg-play-button"></i>
        <i class="icon transited gg-danger"></i>
      </div>
    </button>
    <NuxtLink v-if="started" class="chat-room-input-button hover-effect pointer" :class="colorScheme.dark" to="/chat">
      <i class="gg-mail"></i>
    </NuxtLink>
  </div>
</template>

<script>
export default {
  name: "ChatRoomInput",
  data() {
    return {
      roomIDInput: "",
      pattern: "^[0-9a-zA-Z]{1,30}$"
    }
  },
  computed: {
    colorScheme() {
      return this.$store.state.configuration.colorScheme
    },
    started() {
      return this.$store.state.websocket.started
    },
    roomID() {
      return this.$store.state.websocket.roomID
    },
    valid() {
      return new RegExp(this.pattern).test(this.roomIDInput)
    },
    validClassObject() {
      return {"second-icon-shown": !this.valid}
    }
  },
  mounted() {
    this.roomIDInput = this.roomID;
  },
  methods: {
    applyRoomId() {
      const re = new RegExp(this.pattern)
      if (re.test(this.roomIDInput)) {
        this.$store.commit("websocket/setRoomID", this.roomIDInput)
        this.$router.push({path: 'chat'})
      }
    }
  },
}
</script>

<style lang="scss" scoped>
.chat-room-input-skeleton {
  display: flex;
  justify-content: center;

  .chat-room-input-button {
    padding: 0;
    height: spacing(6);
    width: spacing(6);
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 0 spacing(1) spacing(1) 0;

    .input-button-icons {
      height: spacing(6);
      width: spacing(6);
      position: relative;
      overflow: hidden;
      display: flex;
      justify-content: center;
      align-items: center;

      .icon {
        position: absolute;
        transition: $transition-time-base;

        &:first-child {
          top: 13px;
        }

        &:last-child {
          top: 100px;
        }
      }

      &.second-icon-shown {
        .icon {
          &:first-child {
            top: -100px;
          }

          &:last-child {
            top: 14px;
          }
        }
      }

    }
  }

  .chat-room-input-wrapper {
    height: spacing(4);
    width: 100%;
    border-radius: spacing(1) 0 0 spacing(1);
    padding: spacing(1);

    .chat-room-input {
      color: inherit;
      background: none;
      height: 100%;
      width: 100%;
      border: none;

      &:focus {
        outline: none;
      }
    }
  }
}
</style>
