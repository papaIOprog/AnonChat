export const state = () => ({
  stickyChat: true,
  colorScheme: {
    base: {"bg-color-primary": true},
    light: {"bg-color-primary-light": true},
    dark: {"bg-color-primary-dark": true}
  }
})

export const mutations = {
  unstick(state) {
    state.stickyChat = false;
  },
  stick(state) {
    state.stickyChat = true;
  },
}
