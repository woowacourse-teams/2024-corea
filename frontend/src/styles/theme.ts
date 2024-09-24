const COLOR = {
  white: "#ffffff",
  grey0: "#F3F3F3",
  grey1: "#c6c6c6",
  grey2: "#919191",
  grey3: "#5e5e5e",
  grey4: "#303030",
  black: "#000000",
  primary1: "#D9F3FF",
  primary2: "#84AEE1",
  primary3: "#607999",
  secondary: "#ffaaaf",
  lightGrass: "#D7E4D8",
  grass: "#33B939",
  error: "#FF5F5F",
};

const TEXT = {
  //22px
  xLarge: "2.2rem/2.2rem Noto Sans KR",
  xLarge_bold: "700 2.2rem/2.2rem Noto Sans KR",
  //20px
  large: "2.0rem/2.0rem Noto Sans KR",
  large_bold: "600 2.0rem/2.0rem Noto Sans KR",
  //18px
  medium: "1.8rem/1.8rem Noto Sans KR",
  medium_bold: "600 1.8rem/1.8rem Noto Sans KR",
  //16px
  small: "1.6rem/1.6rem Noto Sans KR",
  small_bold: "600 1.6rem/1.6rem Noto Sans KR",
  //14px
  semiSmall: "1.4rem/1.4rem Noto Sans KR",
  //12px
  xSmall: "1.2rem/1.2rem Noto Sans KR",
};

const BOX_SHADOW = {
  light: "0 1px 1px rgb(0 0 0 / 10%)",
  regular: "0 4px 4px rgb(0 0 0 / 10%)",
};

export const theme = {
  COLOR,
  TEXT,
  BOX_SHADOW,
};

export type ThemeType = typeof theme;
