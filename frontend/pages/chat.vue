<template>
  <div class="application-container">
    <Chat></Chat>
  </div>
</template>

<script>

export default {
  transition: 'main',

  mounted() {
    this.$store.dispatch("websocket/connect")
  }
}
</script>

<style lang="scss" scoped>

</style>
