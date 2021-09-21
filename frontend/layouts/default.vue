<template>
  <div class="application-body scrollable-y" :class="colorScheme.base">
    <Nuxt/>
  </div>
</template>

<script>

export default {
  name: "Default",
  data() {
    return {}
  },
  computed: {
    colorScheme() {
      return this.$store.state.configuration.colorScheme
    }
  },
  mounted() {
    this.$store.dispatch("websocket/init")
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
