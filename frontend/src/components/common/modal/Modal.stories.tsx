import Modal from "./Modal";
import { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "Common/Modal",
  component: Modal,
  parameters: {
    viewport: {
      viewports: {
        small: {
          name: "Small",
          styles: { width: "320px", height: "568px" },
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
} satisfies Meta<typeof Modal>;

export default meta;

type Story = StoryObj<typeof Modal>;

export const Default: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    hasCloseButton: true,
    children: <div>모달 콘텐츠</div>,
  },
  parameters: {
    docs: { disable: true },
  },
};

export const SmallViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    hasCloseButton: true,
    children: <div>모달 콘텐츠</div>,
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
    hasCloseButton: true,
    children: <div>모달 콘텐츠</div>,
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
    hasCloseButton: true,
    children: <div>모달 콘텐츠</div>,
  },
  parameters: {
    viewport: {
      defaultViewport: "large",
    },
    docs: { disable: true },
  },
};
