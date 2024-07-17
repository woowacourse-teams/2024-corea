import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { getMyReviewers } from "@/apis/my.api";

const MyReviewer = () => {
  const [reviewerData, setReviewerData] = useState<ReviewerInfo[]>([]);

  const fetchReviewerData = async () => {
    const res = await getMyReviewers();
    setReviewerData(res);
  };

  useEffect(() => {
    fetchReviewerData();
  }, []);

  if (reviewerData.length === 0) {
    return <>아직 리뷰어가 매칭되지 않았습니다! 조금만 기다려주세요🤗</>;
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
            <Link to={reviewer.link}>
              <S.PRLink>
                <Icon kind="link" />
                바로가기
              </S.PRLink>
            </Link>
          </S.MyReviewerContent>
          <S.MyReviewerContent>
            <Button text="피드백 작성" onClick={() => alert("버튼 클릭 완료!")} />
          </S.MyReviewerContent>
        </S.MyReviewerWrapper>
      ))}
    </S.MyReviewerContainer>
  );
};

export default MyReviewer;
