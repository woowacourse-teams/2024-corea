import { Meta, StoryObj } from "@storybook/react";
import React from "react";
import RankingCard from "@/components/ranking/rankingCard/RankingCard";
import { RankingData } from "@/@types/ranking";
import rankingInfo from "@/mocks/mockResponse/rankingInfo.json";

const sampleRankingData: RankingData[] = rankingInfo.responses.re;

const meta: Meta<typeof RankingCard> = {
  title: "Shared/RoomCardModal",
  component: RankingCard,
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

type Story = StoryObj<typeof RankingCard>;

export const Default: Story = {
  args: {
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: sampleRankingData,
  },
  parameters: {
    docs: { disable: true },
  },
};

export const SmallViewport: Story = {
  args: {
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: sampleRankingData,
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
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: sampleRankingData,
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
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: sampleRankingData,
  },
  parameters: {
    viewport: {
      defaultViewport: "large",
    },
    docs: { disable: true },
  },
};
