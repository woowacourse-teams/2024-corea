import { ToastProvider } from "../src/providers/ToastProvider";
import GlobalStyles from "../src/styles/globalStyles";
import { theme } from "../src/styles/theme";
import type { Preview } from "@storybook/react";
import React from "react";
import { ThemeProvider } from "styled-components";

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
};

export const decorators = [
  (Story) => (
    <ThemeProvider theme={theme}>
      <ToastProvider>
        <div id="toast"></div>
        <div id="modal"></div>
        <GlobalStyles />
        <Story />
      </ToastProvider>
    </ThemeProvider>
  ),
];

export default preview;
