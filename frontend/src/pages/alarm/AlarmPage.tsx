import Profile from "@/components/common/profile/Profile";
import * as S from "@/pages/alarm/AlarmPage.style";

const AlarmPage = () => {
  return (
    <S.Layout>
      <S.AlarmList>
        <S.AlarmItem>
          <S.ProfileWrapper>
            <Profile imgSrc={"www.myProfile.jpg"} size={40} />
          </S.ProfileWrapper>
          <S.ContentWrapper>
            <S.Content>
              <span>00kang</span> 님이 <span>프리코스 3주차 로또 미션 - JavaScript</span> 미션에
              대해 코드리뷰를 완료했습니다.
            </S.Content>
            <S.TimeStamp>2024년 44월 44일</S.TimeStamp>
          </S.ContentWrapper>
        </S.AlarmItem>
      </S.AlarmList>
    </S.Layout>
  );
};

export default AlarmPage;
