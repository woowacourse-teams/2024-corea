import ContentSection from "./ContentSection";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/ContentSection",
  component: ContentSection,
  parameters: {
    docs: {
      description: {
        component: "콘텐트 섹션 컴포넌트",
      },
    },
  },
  argTypes: {
    title: {
      description: "ContentSection 제목",
    },
  },
} satisfies Meta<typeof ContentSection>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    title: "제목",
  },
  render: (args) => (
    <ContentSection title={args.title}>
      ContentSection에는 모든 것이 들어갈 수 있습니다!
    </ContentSection>
  ),
};
