import { CSSObject, Interpolation, css } from "styled-components";
import { SCREEN } from "@/constants/media";

export type Breakpoints = "small" | "medium" | "large";

export const breakpoints: Record<Breakpoints, string> = {
  small: `@media (max-width: ${SCREEN.SMALL}px)`,
  medium: `@media (min-width: ${SCREEN.SMALL}px) and (max-width: ${SCREEN.MEDIUM}px)`,
  large: `@media (min-width: ${SCREEN.MEDIUM}px)`,
} as const;

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
