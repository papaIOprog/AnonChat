export const state = () => ({

  messagesLimit: 6,
  stickyChat: true,
  selectedColor: "primary",
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
  changeColor(state, colorName) {
    const color = Colors[colorName];
    if (color != null) {
      this.state.selectedColor = color;
      const bgColor = "bg-color-" + color;
      const bgColorLight = "bg-color-" + color + "-light";
      const bgColorDark = "bg-color-" + color + "-dark";
      state.colorScheme.base = {[bgColor]: true};
      state.colorScheme.light = {[bgColorLight]: true};
      state.colorScheme.dark = {[bgColorDark]: true};
    }
  }
}

export const Colors = {
  BLUE: "blue",
  INDIGO: "indigo",
  PURPLE: "purple",
  PINK: "pink",
  RED: "red",
  ORANGE: "orange",
  YELLOW: "yellow",
  GREEN: "green",
  TEAL: "teal",
  CYAN: "cyan",
  PRIMARY: "primary",
  SECONDARY: "secondary",
}
