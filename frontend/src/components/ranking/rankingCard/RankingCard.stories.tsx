import { Meta, StoryObj } from "@storybook/react";
import RankingCard from "@/components/ranking/rankingCard/RankingCard";
import { RankingData } from "@/@types/ranking";
import rankingInfo from "@/mocks/mockResponse/rankingInfo.json";

const sampleRankingData: RankingData[] = rankingInfo.responses.re;

const meta: Meta<typeof RankingCard> = {
  title: "rankingCard/RankingCard",
  component: RankingCard,
};

export default meta;

type Story = StoryObj<typeof RankingCard>;

export const Default: Story = {
  args: {
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: sampleRankingData,
  },
};

export const EmptyRankingData: Story = {
  args: {
    title: "평점 좋은 리뷰어 랭킹",
    rankingData: [],
  },
};
