<template>
  <div class="settings-panel-skeleton">
    <div class="settings-header" :class="colorScheme.base">
      <NuxtLink class="settings-header-button hover-effect pointer" :class="colorScheme.dark" to="/"><i
        class="gg-home"></i></NuxtLink>
      <NuxtLink class="settings-header-button hover-effect pointer" :class="colorScheme.dark" to="/chat/"><i
        class="gg-mail"></i></NuxtLink>
    </div>
    <div class="settings-panel-colors" :class="colorScheme.dark">
      <div class="settings-panel-colors-row">
        <div class="settings-panel-colors-tile bg-color-blue hover-effect pointer" @click="changeColor('BLUE')"></div>
        <div class="settings-panel-colors-tile bg-color-red hover-effect pointer" @click="changeColor('RED')"></div>
        <div class="settings-panel-colors-tile bg-color-teal hover-effect pointer" @click="changeColor('TEAL')"></div>

      </div>
      <div class="settings-panel-colors-row">
        <div class="settings-panel-colors-tile bg-color-indigo hover-effect pointer"
             @click="changeColor('INDIGO')"></div>
        <div class="settings-panel-colors-tile bg-color-orange hover-effect pointer"
             @click="changeColor('ORANGE')"></div>
        <div class="settings-panel-colors-tile bg-color-cyan hover-effect pointer" @click="changeColor('CYAN')"></div>
      </div>
      <div class="settings-panel-colors-row">
        <div class="settings-panel-colors-tile bg-color-purple hover-effect pointer"
             @click="changeColor('PURPLE')"></div>
        <div class="settings-panel-colors-tile bg-color-yellow hover-effect pointer"
             @click="changeColor('YELLOW')"></div>
        <div class="settings-panel-colors-tile bg-color-primary hover-effect pointer"
             @click="changeColor('PRIMARY')"></div>
      </div>
      <div class="settings-panel-colors-row">
        <div class="settings-panel-colors-tile bg-color-pink hover-effect pointer" @click="changeColor('PINK')"></div>
        <div class="settings-panel-colors-tile bg-color-green hover-effect pointer" @click="changeColor('GREEN')"></div>
        <div class="settings-panel-colors-tile bg-color-secondary hover-effect pointer"
             @click="changeColor('SECONDARY')"></div>
      </div>
    </div>
    <div class="settings-panel-example" :class="colorScheme.dark">
      <Message :message="message"></Message>
    </div>
  </div>
</template>

<script>
export default {
  name: "SettingsPanel",
  data() {
    return {
      colorScheme: this.$store.state.runtime.colorScheme,
      message: {
        direction: "INCOME",
        fromUser: "System",
        toUser: "You",
        fired: false,
        timestamp: Date.now(),
        type: "NOTIFICATION",
        body: "Hey! This is what the message looks like. ",
      }
    }
  },
  computed: {
    color() {
      return this.$store.state.configuration.selectedColor
    },
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
    changeColor(color) {
      this.$store.commit("configuration/changeColor", color)
    }
  },
}


</script>

<style lang="scss" scoped>
.settings-panel-skeleton {
  .settings-header {
    display: flex;
    justify-content: space-between;
    flex-basis: 0;
    padding-top: spacing(2);


    .settings-header-button {
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

  .settings-panel-colors {
    padding: spacing(2);
    display: flex;
    justify-content: center;

    .settings-panel-colors-row {
      display: flex;
      flex-direction: column;

      .settings-panel-colors-tile {
        height: spacing(8);
        width: spacing(8);
      }

      &:first-child {
        .settings-panel-colors-tile {
          &:first-child {
            border-radius: spacing(1) 0 0 0;
          }

          &:last-child {
            border-radius: 0 0 0 spacing(1);
          }
        }
      }

      &:last-child {
        .settings-panel-colors-tile {
          &:first-child {
            border-radius: 0 spacing(1) 0 0;
          }

          &:last-child {
            border-radius: 0 0 spacing(1) 0;
          }
        }
      }
    }
  }

  .settings-panel-example {
    border-radius: 0 0 spacing(1) spacing(1);
    padding: spacing(2);
    display: flex;
    justify-content: center;
    overflow: hidden;
  }
}
</style>
