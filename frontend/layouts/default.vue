<template>
  <div class="application-body" :class="colorScheme.base">
    <Nuxt/>
  </div>
</template>

<script>

export default {
  name: "Default",
  data() {
    return {
      colorScheme: this.$store.state.runtime.colorScheme,
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
    this.$store.dispatch("websocket/init")
    this.colorScheme = this.$store.getters["configuration/getColorScheme"]
  },
  methods: {
    connect() {
      this.$store.dispatch("websocket/connect")
    }
  }
}
</script>

<style lang="scss" scoped>
.application-body {
  width: 100%;
  height: 100vh;
}
</style>
