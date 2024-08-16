import Carousel from "./Carousel";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/Carousel",
  component: Carousel,
  parameters: {
    docs: {
      description: {
        component: "캐러셀 컴포넌트",
      },
    },
  },
} satisfies Meta<typeof Carousel>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    children: <></>,
  },
  render: () => (
    <Carousel>
      <div style={{ height: "100px" }}>화면 1</div>
      <div style={{ height: "100px" }}>화면 2</div>
      <div style={{ height: "100px" }}>화면 3</div>
    </Carousel>
  ),
};
