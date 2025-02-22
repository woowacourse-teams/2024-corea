import { Meta, StoryObj } from "@storybook/react";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
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
    roomInfo: sampleRoomList,
  },
  parameters: {
    docs: { disable: true },
  },
};

export const SmallViewport: Story = {
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: sampleRoomList,
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
    roomInfo: sampleRoomList,
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
    roomInfo: sampleRoomList,
  },
  parameters: {
    viewport: {
      defaultViewport: "large",
    },
    docs: { disable: true },
  },
};
