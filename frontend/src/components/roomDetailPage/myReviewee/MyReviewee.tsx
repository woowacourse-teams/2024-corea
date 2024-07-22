import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/myReviewee/MyReviewee.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { getMyReviewees } from "@/apis/my.api";

const MyReviewee = () => {
  const [revieweeData, setRevieweeData] = useState<ReviewerInfo[]>([]);

  const fetchRevieweeData = async () => {
    const res = await getMyReviewees();
    setRevieweeData(res);
  };

  useEffect(() => {
    fetchRevieweeData();
  }, []);

  if (revieweeData.length === 0) {
    return <>아직 리뷰이가 매칭되지 않았습니다! 조금만 기다려주세요🤗</>;
  }

  return (
    <S.MyRevieweeContainer>
      <S.MyRevieweeWrapper>
        <S.MyRevieweeTitle>아이디</S.MyRevieweeTitle>
        <S.MyRevieweeTitle>PR 링크</S.MyRevieweeTitle>
        <S.MyRevieweeTitle>제출 여부</S.MyRevieweeTitle>
      </S.MyRevieweeWrapper>

      {revieweeData?.map((reviewee) => (
        <S.MyRevieweeWrapper key={reviewee.userId}>
          <S.MyRevieweeContent>{reviewee.username}</S.MyRevieweeContent>
          <S.MyRevieweeContent>
            <Link to={reviewee.link}>
              <S.PRLink>
                <Icon kind="link" />
                바로가기
              </S.PRLink>
            </Link>
          </S.MyRevieweeContent>
          <S.MyRevieweeContent>
            <Button onClick={() => alert("버튼 클릭 완료!")} variant="secondary">
              리뷰 완료
            </Button>
            <Button onClick={() => alert("버튼 클릭 완료!")} variant="primary">
              피드백 작성
            </Button>
          </S.MyRevieweeContent>
        </S.MyRevieweeWrapper>
      ))}
    </S.MyRevieweeContainer>
  );
};

export default MyReviewee;
