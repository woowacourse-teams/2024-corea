import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/profile/profileCard/ProfileCard.style";
import { HoverStyledLink } from "@/styles/common";

const ProfileCard = (profileData: ProfileData) => {
  return (
    <S.ProfileCardContainer>
      <S.ProfileTitle>프로필</S.ProfileTitle>

      <S.ProfileCardWrapper>
        <HoverStyledLink
          to={`https://github.com/${profileData.nickname}`}
          target="_blank"
          rel="noreferrer"
          tabIndex={-1}
        >
          <S.ProfileWrapper>
            <Profile imgSrc={profileData.profileImage} size={110} />
            <S.ProfileNickname>
              <Icon kind="githubLogo" size="2.5rem" />
              {profileData.nickname}
            </S.ProfileNickname>
          </S.ProfileWrapper>
        </HoverStyledLink>

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
                <Icon kind="star" size="1rem" /> {profileData.averageRating}
              </dd>
            </dl>
          </S.ProfileSummaryContainer>
        </S.ProfileInfoWrapper>

        <S.KeywordContainer>
          {profileData.feedbackKeywords.length !== 0 ? (
            profileData.feedbackKeywords.map((keyword) => (
              <S.KeywordWrapper key={keyword}>
                <Icon kind="people" />
                <S.Keyword>{keyword}</S.Keyword>
              </S.KeywordWrapper>
            ))
          ) : (
            <S.KeywordWrapper>
              <Icon kind="people" />
              <S.Keyword>받은 피드백을 기반으로 키워드가 선정됩니다</S.Keyword>
            </S.KeywordWrapper>
          )}
        </S.KeywordContainer>
      </S.ProfileCardWrapper>

      {/* <S.AttitudeScoreWrapper>
        <S.AttitudeScoreText>
          매너잔디<span>{profileData.attitudeScore}점</span>
        </S.AttitudeScoreText>
        <AttitudeScore score={profileData.attitudeScore} />
      </S.AttitudeScoreWrapper> */}
    </S.ProfileCardContainer>
  );
};

export default ProfileCard;
