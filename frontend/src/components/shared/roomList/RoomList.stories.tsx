import RoomList from "./RoomList";
import type { Meta, StoryObj } from "@storybook/react";
import { RoomInfo } from "@/@types/roomInfo";
import roomInfos from "@/mocks/mockResponse/roomInfos.json";

const sampleRoomList = roomInfos.rooms.map((roomInfo) => ({
  ...roomInfo,
  roomStatus: roomInfo.roomStatus as "OPEN" | "CLOSE" | "PROGRESS" | "FAIL",
  participationStatus: roomInfo.participationStatus as
    | "NOT_PARTICIPATED"
    | "PARTICIPATED"
    | "MANAGER"
    | "PULL_REQUEST_NOT_SUBMITTED",

  memberRole: roomInfo.memberRole as "BOTH" | "REVIEWER" | "REVIEWEE" | "NONE",
  classification: roomInfo.classification as "ALL" | "FRONTEND" | "BACKEND" | "ANDROID",
})) satisfies RoomInfo[];

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
    roomType: {
      description: "방의 타입",
      control: { type: "select" },
      options: ["participated", "opened", "closed"],
    },
  },
} satisfies Meta<typeof RoomList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const SmallViewport: Story = {
  args: {
    roomList: sampleRoomList,
    hasNextPage: false,
    isFetchingNextPage: false,
    roomType: "participated",
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
    isFetchingNextPage: false,
    roomType: "opened",
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
    isFetchingNextPage: false,
    roomType: "closed",
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
    isFetchingNextPage: false,
    roomType: "closed",
  },
  parameters: {
    viewport: {
      defaultViewport: "desktop",
    },
  },
};
