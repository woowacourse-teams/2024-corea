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
    hasNextPage: {
      description: "다음 페이지가 있는지 여부",
      control: { type: "boolean" },
    },
    onLoadMore: {
      description: "더 보기 버튼 클릭 시 호출될 함수",
      action: "clicked",
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

export const SmallViewport: Story = {
  args: {
    roomList: sampleRoomList,
    hasNextPage: false,
  },
  parameters: {
    viewport: {
      defaultViewport: "mobile1",
    },
  },
};

export const SmallViewport_With_NextPage: Story = {
  args: {
    roomList: sampleRoomList,
    hasNextPage: true,
    onLoadMore: () => console.log("Load more clicked"),
  },
  parameters: {
    viewport: {
      defaultViewport: "mobile1",
    },
  },
};

export const MediumViewport: Story = {
  args: {
    roomList: sampleRoomList,
    hasNextPage: false,
  },
  parameters: {
    viewport: {
      defaultViewport: "tablet",
    },
  },
};

export const LargeViewport: Story = {
  args: {
    roomList: sampleRoomList,
    hasNextPage: false,
  },
  parameters: {
    viewport: {
      defaultViewport: "desktop",
    },
  },
};
