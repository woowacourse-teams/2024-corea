import Icon from "../icon/Icon";
import IconButton from "./IconButton";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/IconButton",
  component: IconButton,
  parameters: {
    docs: {
      description: {
        component: "아이콘 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    text: {
      description: "아이콘 버튼 텍스트",
      control: { type: "text" },
    },
    children: {
      description: "아이콘 또는 이미지 컴포넌트",
    },
  },
} satisfies Meta<typeof IconButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    children: <Icon kind="person" />,
    text: "person",
  },
};

export const With_Img: Story = {
  args: {
    children: (
      <img
        src="https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004"
        width="32px"
      />
    ),
    text: "racingCar",
  },
};
