import RoomCard from "./RoomCard";
import type { Meta, StoryObj } from "@storybook/react";
import {
  Classification,
  MemberRole,
  ParticipationStatus,
  RoomInfo,
  RoomStatus,
} from "@/@types/roomInfo";
import roomInfo from "@/mocks/mockResponse/roomInfo.json";

const sampleRoomList = {
  ...roomInfo,
  roomStatus: roomInfo.roomStatus as RoomStatus,
  participationStatus: roomInfo.participationStatus as ParticipationStatus,
  memberRole: roomInfo.memberRole as MemberRole,
  classification: roomInfo.classification as Classification,
} satisfies RoomInfo;

const meta = {
  title: "shared/RoomCard",
  component: RoomCard,
  parameters: {
    docs: {
      description: {
        component: "방 정보를 표시하는 카드 컴포넌트",
      },
    },
  },
} satisfies Meta<typeof RoomCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: { roomInfo: sampleRoomList },
};

export const OpenedRoom: Story = {
  args: {
    roomInfo: {
      ...sampleRoomList,
      roomStatus: "OPEN",
    },
  },
};

export const ClosedRoom: Story = {
  args: {
    roomInfo: {
      ...sampleRoomList,
      roomStatus: "CLOSE",
    },
  },
};

export const LongTitle: Story = {
  args: {
    roomInfo: {
      ...sampleRoomList,
      title: "이것은 아주 긴 제목입니다. 제목이 길 때 어떻게 보이는지 테스트합니다.",
    },
  },
};

export const ManyKeywords: Story = {
  args: {
    roomInfo: {
      ...sampleRoomList,
      keywords: ["React", "TypeScript", "Storybook", "Jest", "Cypress", "Redux", "GraphQL"],
    },
  },
};
