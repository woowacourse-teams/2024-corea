import RoomList from "./RoomList";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import { BrowserRouter } from "react-router-dom";
import { RoomInfo } from "@/@types/roomInfo";
import roomInfos from "@/mocks/mockResponse/roomInfos.json";

const sampleRoomList: RoomInfo[] = roomInfos.roomInfo;

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
    onLoadMore: () => {},
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
