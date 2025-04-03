import RoomCardSkeleton from "../roomCard/RoomCardSkeleton";
import * as S from "./RoomList.style";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";

const RoomListSkeleton = ({ count = 8 }: { count?: number }) => (
  <DelaySuspense>
    <S.ScreenReader role="status" aria-live="polite">
      방 목록을 불러오는 중입니다...
    </S.ScreenReader>
    {Array.from({ length: count }).map((_, idx) => (
      <RoomCardSkeleton key={idx} />
    ))}
  </DelaySuspense>
);

export default RoomListSkeleton;
