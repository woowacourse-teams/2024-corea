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
      control: "text",
    },
    button: {
      description: "ContentSection 버튼 (옵션)",
      control: "object",
    },
  },
} satisfies Meta<typeof ContentSection>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    title: "제목만 있는 기본",
  },
  render: (args) => (
    <ContentSection title={args.title}>
      ContentSection에는 모든 것이 들어갈 수 있습니다!
    </ContentSection>
  ),
};

export const WithButton: Story = {
  args: {
    title: "버튼이 있는 ContentSection",
    button: {
      label: "클릭하세요",
      onClick: () => alert("버튼이 클릭되었습니다!"),
    },
  },
  render: (args) => (
    <ContentSection {...args}>이 ContentSection에는 버튼이 포함되어 있습니다.</ContentSection>
  ),
};
