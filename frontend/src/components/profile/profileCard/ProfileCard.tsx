import * as S from "./ProfileCard.style";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import AttitudeScore from "@/components/profile/attitudeScore/AttitudeScore";
import IconKind from "@/@types/icon";

interface UserInfo {
  title: string;
  value: number;
  iconKind?: IconKind;
}

const ProfileCard = (profileData: ProfileData) => {
  const userInfo: UserInfo[] = [
    {
      title: "리뷰한 개수",
      value: profileData.givenReviewCount,
    },
    {
      title: "리뷰 받은 개수",
      value: profileData.receivedReviewCount,
    },
    {
      title: "피드백 받은 개수",
      value: profileData.feedbackCount,
    },
    {
      title: "평균 평점",
      value: profileData.averageRating,
      iconKind: "star",
    },
  ];

  return (
    <S.ProfileCardContainer>
      <S.ProfileTitle>프로필</S.ProfileTitle>

      <S.ProfileCardWrapper>
        <S.ProfileWrapper>
          <Profile imgSrc={profileData.profileImage} size={110} />
          <S.ProfileNickname>{profileData.nickname}</S.ProfileNickname>
        </S.ProfileWrapper>

        <S.ProfileInfoWrapper>
          <S.ProfileSummaryContainer>
            <dl>
              <dt>리뷰한 개수</dt>
              <dd>{profileData.givenReviewCount}개</dd>
            </dl>
            <dl>
              <dt>리뷰 받은 개수</dt>
              <dd>{profileData.receivedReviewCount}개</dd>
            </dl>
            <dl>
              <dt>피드백 받은 개수</dt>
              <dd>{profileData.feedbackCount}개</dd>
            </dl>
            <dl>
              <dt>평균 평점</dt>
              <dd>
                <Icon kind="star" size="1rem" /> 1.4
              </dd>
            </dl>
          </S.ProfileSummaryContainer>
        </S.ProfileInfoWrapper>

        <S.KeywordContainer>
          {profileData.feedbackKeywords.map((keyword) => (
            <S.KeywordWrapper key={keyword}>
              <Icon kind="people" />
              <S.Keyword>{keyword}</S.Keyword>
            </S.KeywordWrapper>
          ))}
        </S.KeywordContainer>
      </S.ProfileCardWrapper>

      <S.AttitudeScoreWrapper>
        <S.AttitudeScoreText>
          매너잔디<span>{profileData.attitudeScore}점</span>
        </S.AttitudeScoreText>
        <AttitudeScore score={profileData.attitudeScore} />
      </S.AttitudeScoreWrapper>
    </S.ProfileCardContainer>
  );
};

export default ProfileCard;
