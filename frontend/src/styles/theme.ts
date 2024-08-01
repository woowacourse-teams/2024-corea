const COLOR = {
  white: "#ffffff",
  grey1: "#c6c6c6",
  grey2: "#919191",
  grey3: "#5e5e5e",
  grey4: "#303030",
  black: "#000000",
  primary1: "#D9F3FF",
  primary2: "#84AEE1",
  primary3: "#607999",
  secondary: "#FF3D45",
  lightGrass: "#D7E4D8",
  grass: "#00B707",
};

const TEXT = {
  xLarge: "800 1.8rem/1.8rem hanna",
  large: "700 1.4rem/1.4rem hanna",
  medium: "500 1.2rem/1.2rem hanna",
  small: "400 1.0rem/1.0rem hanna",
  semiSmall: "400 0.8rem/0.8rem hanna",
  xSmall: "400 0.6rem/0.6rem hanna",
};

export default TEXT;
export const theme = {
  COLOR,
  TEXT,
};

export type ThemeType = typeof theme;
