import {
  displayLeftTime,
  formatDateTimeString,
  formatDday,
  formatLeftTime,
} from "@/utils/dateFormatter";

describe("날짜 포메팅 유틸 함수 테스트", () => {
  beforeAll(() => {
    const mockDate = new Date("2024-10-02T10:30:00+09:00");
    const OriginalDate = Date;

    jest.spyOn(global, "Date").mockImplementation((value) => {
      return value ? new OriginalDate(value) : new OriginalDate(mockDate);
    });
  });

  afterAll(() => {
    jest.restoreAllMocks();
  });

  describe("formatDateTimeString 함수 테스트", () => {
    it("string 타입의 date를 받아 '연-월-일 시간:분' 포멧팅으로 반환 테스트", () => {
      const mockDeadlineDate = "2024-10-14T12:56:31";
      const deadline = formatDateTimeString(mockDeadlineDate);

      expect(deadline).toBe("24-10-14 12:56");
    });
  });

  describe("formatDday 함수 테스트", () => {
    it("날짜가 5일이 남은 경우 'D-5'가 반환", () => {
      const dateString = "2024-10-07T12:00:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("D-5");
    });

    it("날짜가 1일이 남았고, 시간상으로 24시간 이상 남은 경우 'D-1'이 반환", () => {
      const dateString = "2024-10-03T10:35:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("D-1");
    });

    it("날짜가 1일 남았고, 시간상으로 24시간 이하로 남은 경우 'D-0'이 반환", () => {
      const dateString = "2024-10-03T10:29:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("D-Day");
    });

    it("날짜가 0일 남았고, 시간이 남은 경우 'D-Day'가 반환", () => {
      const dateString = "2024-10-02T23:29:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("D-Day");
    });

    it("날짜가 0일 남았지만, 시간이 지난 경우 '종료됨'을 반환", () => {
      const dateString = "2024-10-02T10:29:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("종료됨");
    });

    it("날짜가 지난 경우 '종료됨'을 반환", () => {
      const dateString = "2024-10-01T10:35:00+09:00";
      const dDay = formatDday(dateString);

      expect(dDay).toBe("종료됨");
    });
  });

  describe("formatLeftTime 함수 테스트", () => {
    it("종료 시간이 1시간 이상이 남은 경우 'N시간 M분 전' 반환 테스트", () => {
      const dateString = "2024-10-02T15:00:30+09:00";
      const leftTime = formatLeftTime(dateString);

      expect(leftTime).toBe("4시간 30분 전");
    });

    it("종료 시간이 1시간 미만으로 남은 경우 'M분 전' 반환 테스트", () => {
      const dateString = "2024-10-02T11:15:30+09:00";
      const leftTime = formatLeftTime(dateString);

      expect(leftTime).toBe("45분 전");
    });

    it("종료 시간이 되었으면 '곧 종료' 반환 테스트", () => {
      const dateString = "2024-10-02T10:30:30+09:00";
      const leftTime = formatLeftTime(dateString);

      expect(leftTime).toBe("곧 종료");
    });
  });

  describe("displayLeftTime 함수 테스트", () => {
    it("2일 이상 남은 경우 남은 디데이 반환 테스트", () => {
      const dateString = "2024-10-05T10:30:30+09:00";
      const leftTime = displayLeftTime(dateString);

      expect(leftTime).toBe("D-3");
    });

    it("날짜가 1일이 남았고, 시간상으로 24시간 이상 남은 경우 'D-1'이 반환", () => {
      const dateString = "2024-10-03T10:31:30+09:00";
      const leftTime = displayLeftTime(dateString);

      expect(leftTime).toBe("D-1");
    });

    it("날짜가 1일이 남았고, 시간상으로 24시간 미만 남은 경우 남은 시간이 반환", () => {
      const dateString = "2024-10-03T10:29:30+09:00";
      const leftTime = displayLeftTime(dateString);

      expect(leftTime).toBe("23시간 59분 전");
    });
  });
});
