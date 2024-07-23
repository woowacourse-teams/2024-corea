import RoomCard from "./RoomCard";
import type { Meta, StoryObj } from "@storybook/react";
import { RoomInfo } from "@/@types/roomInfo";

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
  args: { roomInfo: sampleRoomInfo },
};

export const OpenedRoom: Story = {
  args: {
    roomInfo: {
      ...sampleRoomInfo,
      isClosed: false,
    },
  },
};

export const ClosedRoom: Story = {
  args: {
    roomInfo: {
      ...sampleRoomInfo,
      isClosed: true,
    },
  },
};

export const LongTitle: Story = {
  args: {
    roomInfo: {
      ...sampleRoomInfo,
      title: "이것은 아주 긴 제목입니다. 제목이 길 때 어떻게 보이는지 테스트합니다.",
    },
  },
};

export const ManyKeywords: Story = {
  args: {
    roomInfo: {
      ...sampleRoomInfo,
      keywords: ["React", "TypeScript", "Storybook", "Jest", "Cypress", "Redux", "GraphQL"],
    },
  },
};
