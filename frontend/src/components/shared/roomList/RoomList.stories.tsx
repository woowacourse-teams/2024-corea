import RoomList from "./RoomList";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import { BrowserRouter } from "react-router-dom";
import { RoomInfo } from "@/@types/roomInfo";

const meta = {
  title: "shared/RoomList",
  component: RoomList,
  parameters: {
    docs: {
      description: {
        component: "방 목록을 표시하는 컴포넌트",
      },
    },
  },
  decorators: [(Story) => <BrowserRouter>{Story()}</BrowserRouter>],
  argTypes: {
    roomList: {
      description: "방 정보 목록",
      control: { type: "object" },
    },
  },
} satisfies Meta<typeof RoomList>;

export default meta;

type Story = StoryObj<typeof meta>;

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

const sampleRoomList: RoomInfo[] = [
  sampleRoomInfo,
  { ...sampleRoomInfo, id: 2 },
  { ...sampleRoomInfo, id: 3 },
  { ...sampleRoomInfo, id: 4 },
];

export const Small_Device: Story = {
  args: {
    roomList: sampleRoomList,
  },
  parameters: {
    viewport: {
      defaultViewport: "mobile1",
    },
  },
};

export const Medium_Device: Story = {
  args: {
    roomList: sampleRoomList,
  },
  parameters: {
    viewport: {
      defaultViewport: "tablet",
    },
  },
};

export const Large_Device: Story = {
  args: {
    roomList: sampleRoomList,
  },
  parameters: {
    viewport: {
      defaultViewport: "desktop",
    },
  },
};
