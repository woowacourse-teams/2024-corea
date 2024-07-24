import { CSSObject, Interpolation, css } from "styled-components";

export type Breakpoints = "small" | "medium" | "large";

export const breakpoints: Record<Breakpoints, string> = {
  small: "@media (max-width: 639px)",
  medium: "@media (min-width: 640px) and (max-width: 1047px)",
  large: "@media (min-width: 1048px)",
};

const media = Object.entries(breakpoints).reduce(
  (acc, [key, value]) => {
    acc[key as Breakpoints] = (
      first: CSSObject | TemplateStringsArray,
      ...interpolations: Interpolation<object>[]
    ) => css`
      ${value} {
        ${css(first, ...interpolations)}
      }
    `;
    return acc;
  },
  {} as Record<
    Breakpoints,
    (
      first: CSSObject | TemplateStringsArray,
      ...interpolations: Interpolation<object>[]
    ) => ReturnType<typeof css>
  >,
);

export default media;
