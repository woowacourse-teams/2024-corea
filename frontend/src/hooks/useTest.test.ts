import { act, renderHook } from "@testing-library/react";
import useTest from "@/hooks/useTest";

test("훅 테스트", () => {
  const { result } = renderHook(() => useTest());

  act(() => {
    result.current.func(4);
  });

  const res = result.current.data;

  expect(res).toBe(4);
});
