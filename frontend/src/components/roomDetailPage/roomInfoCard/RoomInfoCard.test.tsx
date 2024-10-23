import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
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

describe("RoomInfoCard 컴포넌트 테스트", () => {
  beforeAll(() => {
    const mockDate = new Date("2024-10-02T10:30:00+09:00");
    const OriginalDate = Date;

    jest.spyOn(global, "Date").mockImplementation((value) => {
      return value ? new OriginalDate(value) : new OriginalDate(mockDate);
    });
  });

  const renderWithRouter = (ui: React.ReactElement) => {
    return render(
      <MemoryRouter>
        <ThemeProvider theme={theme}>{ui}</ThemeProvider>
      </MemoryRouter>,
    );
  };

  it("'모집'중인 방에 2일 이상 남으면 '리뷰 마감까지 남은 일', '모집 마감까지 남은 일'이 보인다", async () => {
    renderWithRouter(<RoomInfoCard roomInfo={mockBaseRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("D-3");
    expect(reviewLeftDay).toHaveTextContent("D-6");
  });

  it("'모집'이 24시간 미만, '리뷰'가 24시간 이상 남은 경우 '모집 마감까지 남은 시간', '리뷰 마감까지 남은 일'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-03T00:30:00+09:00",
      reviewDeadline: "2024-10-05T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("14시간 0분 전");
    expect(reviewLeftDay).toHaveTextContent("D-2");
  });

  it("'모집'이 24시간 미만, '리뷰'가 24시간 미만인 경우 '모집 마감까지 남은 시간', '리뷰 마감까지 남은 시간'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-02T12:30:00+09:00",
      reviewDeadline: "2024-10-03T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("2시간 0분 전");
    expect(reviewLeftDay).toHaveTextContent("14시간 0분 전");
  });

  it("'모집'완료 후 '진행 중'으로 바뀌었을 때 '리뷰'가 24시간 이상인 경우 '리뷰 마감까지 남은 일'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-01T12:30:00+09:00",
      reviewDeadline: "2024-10-04T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("");
    expect(reviewLeftDay).toHaveTextContent("D-1");
  });

  it("'모집'완료 후 '진행 중'으로 바뀌었을 때 '리뷰'가 24시간 미만인 경우 '리뷰 마감까지 남은 시간'이 보인다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      recruitmentDeadline: "2024-10-01T12:30:00+09:00",
      reviewDeadline: "2024-10-03T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("");
    expect(reviewLeftDay).toHaveTextContent("14시간 0분 전");
  });

  it("'종료됨' 상태인 방에서는 남은 기간이 보이지 않는다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "CLOSE",
      recruitmentDeadline: "2024-10-01T12:30:00+09:00",
      reviewDeadline: "2024-10-03T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("");
    expect(reviewLeftDay).toHaveTextContent("");
  });

  it("'실패' 상태인 방에서는 남은 기간이 보이지 않는다", async () => {
    const mockRoomInfo: RoomInfo = {
      ...mockBaseRoomInfo,
      roomStatus: "FAIL",
      recruitmentDeadline: "2024-10-01T12:30:00+09:00",
      reviewDeadline: "2024-10-03T00:30:00+09:00",
    };

    renderWithRouter(<RoomInfoCard roomInfo={mockRoomInfo} />);

    const recruitLeftDay = screen.getByTestId("recruitLeftTime");
    const reviewLeftDay = screen.getByTestId("reviewLeftTime");

    expect(recruitLeftDay).toHaveTextContent("");
    expect(reviewLeftDay).toHaveTextContent("");
  });
});
