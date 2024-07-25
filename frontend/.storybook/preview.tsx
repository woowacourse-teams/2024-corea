import { ToastProvider } from "../src/providers/ToastProvider";
import GlobalStyles from "../src/styles/globalStyles";
import { theme } from "../src/styles/theme";
import type { Preview } from "@storybook/react";
import React from "react";
import { BrowserRouter } from "react-router-dom";
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
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <GlobalStyles />
          <Story />
        </ToastProvider>
      </ThemeProvider>
    </BrowserRouter>
  ),
];

export default preview;
