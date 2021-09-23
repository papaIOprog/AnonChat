export const state = () => ({
  selectedColor: "primary"
})

export const mutations = {
  changeColor(state, colorName) {
    const color = Colors[colorName];
    if (color != null) {
      state.selectedColor = color;
    }
  }
}

export const getters = {
  getColorScheme(state) {
    const color = state.selectedColor;
    const bgColor = "bg-color-" + color;
    const bgColorLight = "bg-color-" + color + "-light";
    const bgColorDark = "bg-color-" + color + "-dark";
    return {
      base: {[bgColor]: true},
      light: {[bgColorLight]: true},
      dark: {[bgColorDark]: true}
    }
  },
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
