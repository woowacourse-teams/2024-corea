import { useQuery } from "@tanstack/react-query";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewers } from "@/apis/review.api";
import MESSAGES from "@/constants/message";

const MyReviewer = ({ roomId }: { roomId: number }) => {
  const { data: reviewerData } = useQuery({
    queryKey: [QUERY_KEYS.REVIEWERS, roomId],
    queryFn: () => getMyReviewers(roomId),
  });

  if (!reviewerData || reviewerData.length === 0) {
    return <>{MESSAGES.GUIDANCE.EMPTY_REVIEWER}</>;
  }

  return (
    <S.MyReviewerContainer>
      <S.MyReviewerWrapper>
        <S.MyReviewerTitle>아이디</S.MyReviewerTitle>
        <S.MyReviewerTitle>PR 링크</S.MyReviewerTitle>
        <S.MyReviewerTitle>제출 여부</S.MyReviewerTitle>
      </S.MyReviewerWrapper>

      {reviewerData.map((reviewer) => (
        <S.MyReviewerWrapper key={reviewer.userId}>
          <S.MyReviewerContent>{reviewer.username}</S.MyReviewerContent>
          <S.MyReviewerContent>
            <S.PRLink href={reviewer.link}>
              <Icon kind="link" />
              바로가기
            </S.PRLink>
          </S.MyReviewerContent>
          <S.MyReviewerContent>
            <Button size="small" onClick={() => alert("버튼 클릭 완료!")} variant="primary">
              피드백 작성
            </Button>
          </S.MyReviewerContent>
        </S.MyReviewerWrapper>
      ))}
    </S.MyReviewerContainer>
  );
};

export default MyReviewer;
