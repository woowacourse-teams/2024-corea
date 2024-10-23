import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import { ThemeProvider } from "styled-components";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";

const mockBaseRoomInfo: RoomInfo = {
  id: 1,
  manager: "darr",
  reviewerCount: 2,
  bothCount: 3,
  roomStatus: "OPEN",
  participationStatus: "PARTICIPATED",
  memberRole: "BOTH",
  title: "테스트 제목",
  content: "테스트 본문",
  repositoryLink: "테스트 링크",
  thumbnailLink: "테스트 썸네일",
  matchingSize: 5,
  keywords: ["테스트"],
  classification: "BACKEND",
  limitedParticipants: 10,
  recruitmentDeadline: "2024-10-05T10:30:00+09:00",
  reviewDeadline: "2024-10-08T10:30:00+09:00",
  message: "테스트 메세지",
};

describe("RoomCard 컴포넌트 날짜 테스트", () => {
  beforeAll(() => {
    const mockDate = new Date("2024-10-02T10:30:00+09:00");
    const OriginalDate = Date;

    jest.spyOn(global, "Date").mockImplementation((value) => {
      return value ? new OriginalDate(value) : new OriginalDate(mockDate);
    });
  });

  it("'모집'중인 방이 2일 이상 남으면 '모집마감 D-N'이 보인다", async () => {
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockBaseRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("D-3");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'모집'중인 방의 날짜가 1일이 남았고, 시간상으로 24시간 이상 남으면 'D-1'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-03T14:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("D-1");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'모집'중인 방의 날짜가 1일이 남았고, 시간상으로 24시간 미만 남으면 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-03T08:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("22시간 0분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'모집'중인 방 당일인 경우 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-03T08:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("22시간 0분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'모집'중인 방 당일인 경우 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-02T13:35:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("3시간 5분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'모집'중인 방 당일 남은 시간이 없으면 '곧 종료'가 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-02T10:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("모집 마감");
    const leftDay = await screen.findByText("곧 종료");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방에 2일 이상 남으면 '리뷰마감 D-N'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
    };

    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("D-6");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방의 날짜가 1일이 남았고, 시간상으로 24시간 이상 남으면 'D-1'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      recruitmentDeadline: "2024-10-01T14:30:00+09:00",
      reviewDeadline: "2024-10-03T14:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("D-1");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방의 날짜가 1일이 남았고, 시간상으로 24시간 미만 남으면 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      recruitmentDeadline: "2024-10-01T14:30:00+09:00",
      reviewDeadline: "2024-10-03T00:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("14시간 0분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방 당일인 경우 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      recruitmentDeadline: "2024-10-01T14:30:00+09:00",
      reviewDeadline: "2024-10-02T15:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("5시간 0분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방 당일인 경우 남은 시간이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      recruitmentDeadline: "2024-10-01T14:30:00+09:00",
      reviewDeadline: "2024-10-02T12:35:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("2시간 5분 전");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'진행'중인 방 당일 남은 시간이 없으면 '곧 종료'가 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      recruitmentDeadline: "2024-10-01T14:30:00+09:00",
      reviewDeadline: "2024-10-02T10:30:00+09:00",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const text = await screen.findByText("리뷰 마감");
    const leftDay = await screen.findByText("곧 종료");

    expect(text).toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'실패'된 방은 '종료됨'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "FAIL",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const leftDay = await screen.findByText("종료됨");
    const recruitText = screen.queryByText("모집 마감");
    const reviewText = screen.queryByText("리뷰 마감");

    expect(recruitText).not.toBeInTheDocument();
    expect(reviewText).not.toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });

  it("'종료'된 방은 '종료됨'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    const recruitText = screen.queryByText("모집 마감");
    const reviewText = screen.queryByText("리뷰 마감");
    const leftDay = await screen.findByText("종료됨");

    expect(recruitText).not.toBeInTheDocument();
    expect(reviewText).not.toBeInTheDocument();
    expect(leftDay).toBeInTheDocument();
  });
});

describe("RoomCard 컴포넌트 라벨 테스트", () => {
  it("참여중 상태의 방은 '참여' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "OPEN",
      participationStatus: "PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("참여")).toBeInTheDocument();
  });

  it("참여중 상태인데 모집 중인 상태의 방은 '참여'와 '모집 중' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "OPEN",
      participationStatus: "PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("참여")).toBeInTheDocument();
    expect(await screen.findByText("모집 중")).toBeInTheDocument();
  });

  it("모집중 상태의 방은 '모집 중' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "OPEN",
      participationStatus: "NOT_PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("모집 중")).toBeInTheDocument();
  });

  it("모집중 상태인데 참여중인 상태의 방은 '모집 중'과 '참여' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "OPEN",
      participationStatus: "PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("모집 중")).toBeInTheDocument();
    expect(await screen.findByText("참여")).toBeInTheDocument();
  });

  it("진행 중 상태의 방은 '진행 중' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      participationStatus: "NOT_PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("진행 중")).toBeInTheDocument();
  });

  it("진행 중 상태인데 참여중인 상태의 방은 '진행 중'과 '참여' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "PROGRESS",
      participationStatus: "PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("진행 중")).toBeInTheDocument();
    expect(await screen.findByText("참여")).toBeInTheDocument();
  });

  it("종료됨 상태의 방은 '종료' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
      participationStatus: "NOT_PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("종료")).toBeInTheDocument();
  });

  it("종료됨 상태인데 참여중인 상태의 방은 '종료'와 '참여' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
      participationStatus: "PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("종료")).toBeInTheDocument();
    expect(await screen.findByText("참여")).toBeInTheDocument();
  });

  it("메인페이지에서 종료됨 상태인데 매칭실패 상태의 방은 '종료' 라벨만 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
      participationStatus: "NOT_PARTICIPATED",
      message: "",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("종료")).toBeInTheDocument();
  });

  it("마이페이지에서 종료된 방인데 매칭실패 상태의 방은 '종료'와 '매칭실패' 라벨이 표시된다.", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
      participationStatus: "PARTICIPATED",
      message: "방의 최소 참여 인원보다 참가자가 부족하여 매칭이 진행되지 않았습니다.",
    };
    render(
      <ThemeProvider theme={theme}>
        <RoomCard roomInfo={mockRoomInfo} />
      </ThemeProvider>,
    );

    expect(await screen.findByText("종료")).toBeInTheDocument();
    expect(await screen.findByText("매칭 실패")).toBeInTheDocument();
  });
});
