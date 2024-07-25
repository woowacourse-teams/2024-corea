import { Meta, StoryObj } from "@storybook/react";
import React from "react";
import { ThemeProvider } from "styled-components";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
import RoomInfo from "@/mocks/mockResponse/roomInfo.json";

const meta: Meta<typeof RoomCardModal> = {
  title: "Shared/RoomCardModal",
  component: RoomCardModal,
  parameters: {
    viewport: {
      viewports: {
        small: {
          name: "Small",
          styles: { width: "375px", height: "568px" },
        },
        medium: {
          name: "Medium",
          styles: { width: "768px", height: "1024px" },
        },
        large: {
          name: "Large",
          styles: { width: "1200px", height: "1366px" },
        },
      },
    },
  },
};

export default meta;

type Story = StoryObj<typeof RoomCardModal>;

export const Default: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: RoomInfo.roomInfo,
  },
  parameters: {
    docs: { disable: true },
  },
};

export const SmallViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: RoomInfo.roomInfo,
  },
  parameters: {
    viewport: {
      defaultViewport: "small",
    },
    docs: { disable: true },
  },
};

export const MediumViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: RoomInfo.roomInfo,
  },
  parameters: {
    viewport: {
      defaultViewport: "medium",
    },
    docs: { disable: true },
  },
};

export const LargeViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: RoomInfo.roomInfo,
  },
  parameters: {
    viewport: {
      defaultViewport: "large",
    },
    docs: { disable: true },
  },
};
