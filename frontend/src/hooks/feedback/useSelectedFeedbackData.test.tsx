import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { renderHook, waitFor } from "@testing-library/react";
import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";

const queryClient = new QueryClient();

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

describe("useSelectedFeedbackData 훅 테스트", () => {
  describe("받은 피드백 테스트", () => {
    it("받은 피드백 중 총 피드백을 받은 미션이 2개인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        expect(result.current.userFeedbacks?.length).toBe(2);
      });
    });

    it("받은 피드백 중 첫 번째 미션에 피드백 작성해준 사람이 3명인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[0] ?? {};

        expect(Object.keys(feedbackUsers).length).toBe(3);
      });
    });

    it("받은 피드백 중 첫 번째 미션에 피드백 작성해준 사람에게 받은 피드백이 1 ~ 2개인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[0] ?? {};

        Object.entries(feedbackUsers).map(([userName, feedbackInfo]) =>
          expect([1, 2]).toContain(feedbackInfo.length),
        );
      });
    });

    it("받은 피드백 중 두 번째 미션에 피드백 작성해준 사람이 2명인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[1] ?? {};

        expect(Object.keys(feedbackUsers).length).toBe(2);
      });
    });

    it("받은 피드백 중 두 번째 미션에 피드백 작성해준 사람에게 받은 피드백이 1 ~ 2개인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[1] ?? {};

        Object.entries(feedbackUsers).map(([userName, feedbackInfo]) =>
          expect([1, 2]).toContain(feedbackInfo.length),
        );
      });
    });
  });

  describe("받은 피드백 테스트", () => {
    it("작성한 피드백 중 총 피드백을 받은 미션이 1개인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        result.current.setSelectedFeedbackType("쓴 피드백");
      });

      await waitFor(() => {
        expect(result.current.userFeedbacks?.length).toBe(1);
      });
    });

    it("작성한 피드백 중 첫 번째 미션에 피드백 작성해준 사람이 4명인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        result.current.setSelectedFeedbackType("쓴 피드백");
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[0] ?? {};

        expect(Object.keys(feedbackUsers).length).toBe(4);
      });
    });

    it("작성한 피드백 중 첫 번째 미션에 피드백 작성해준 사람에게 받은 피드백이 1 ~ 2개인지 테스트", async () => {
      const { result } = renderHook(() => useSelectedFeedbackData(), {
        wrapper,
      });

      await waitFor(() => {
        result.current.setSelectedFeedbackType("쓴 피드백");
      });

      await waitFor(() => {
        const feedbackUsers = result.current.userFeedbacks?.[0] ?? {};

        Object.entries(feedbackUsers).map(([userName, feedbackInfo]) =>
          expect([1, 2]).toContain(feedbackInfo.length),
        );
      });
    });
  });
});
