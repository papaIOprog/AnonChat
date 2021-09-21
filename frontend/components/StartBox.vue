<template>
  <div>
    <span class="badge normalized">{{ title }}</span>
    <ChatRoomInput></ChatRoomInput>
  </div>
</template>

<script>
export default {
  name: "StartBox",
  computed: {
    started() {
      return this.$store.state.websocket.started
    },
    title() {
      return this.started ? "Disconnect from chat first!" : "Enter your room ID:"
    }
  }
}
</script>

<style scoped>

</style>
