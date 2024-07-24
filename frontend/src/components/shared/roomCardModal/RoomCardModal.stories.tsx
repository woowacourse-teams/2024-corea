import { Meta, StoryObj } from "@storybook/react";
import React from "react";
import { ThemeProvider } from "styled-components";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
import { RoomInfo } from "@/@types/roomInfo";

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

const sampleRoomInfo: RoomInfo = {
  id: 1,
  title: "자바 레이싱 카 - TDD",
  content: "TDD를 배우고 싶은 자 나에게로",
  matchingSize: 3,
  repositoryLink: "https://github.com/example/java-racingcar",
  thumbnailLink:
    "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
  keywords: ["TDD", "클린코드", "자바"],
  currentParticipantSize: 15,
  maximumParticipantSize: 20,
  manager: "김코딩",
  recruitmentDeadline: "2024-07-30T15:00",
  reviewDeadline: "2024-08-10T23:59",
  isParticipated: true,
  isClosed: false,
};

export const Default: Story = {
  args: {
    isOpen: true,
    onClose: () => alert("Modal closed"),
    roomInfo: sampleRoomInfo,
  },
  parameters: {
    docs: { disable: true },
  },
};

export const SmallViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => alert("Modal closed"),
    roomInfo: sampleRoomInfo,
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
    onClose: () => alert("Modal closed"),
    roomInfo: sampleRoomInfo,
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
    onClose: () => alert("Modal closed"),
    roomInfo: sampleRoomInfo,
  },
  parameters: {
    viewport: {
      defaultViewport: "large",
    },
    docs: { disable: true },
  },
};
