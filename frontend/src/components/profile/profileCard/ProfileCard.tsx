import * as S from "./ProfileCard.style";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import AttitudeScore from "@/components/profile/attitudeScore/AttitudeScore";
import IconKind from "@/@types/icon";

interface UserInfo {
  title: string;
  value: number;
  iconKind: IconKind;
}

const ProfileCard = (profileData: ProfileData) => {
  const userInfo: UserInfo[] = [
    {
      title: "리뷰한 개수",
      value: profileData.givenReviewCount,
      iconKind: "pencil",
    },
    {
      title: "리뷰 받은 개수",
      value: profileData.receivedReviewCount,
      iconKind: "pencil",
    },
    {
      title: "피드백 받은 개수",
      value: profileData.feedbackCount,
      iconKind: "pencil",
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
          <S.ProfileInfoTable>
            {userInfo.map((info) => (
              <tr key={info.title}>
                <S.InfoTitle>{info.title}</S.InfoTitle>
                <td>
                  <S.ProfileFlex>
                    <Icon kind={info.iconKind} size="1rem" />{" "}
                    <S.InfoCount>
                      {" "}
                      {"   " + info.value.toLocaleString()}
                      {info.iconKind === "pencil" && "개"}
                    </S.InfoCount>
                  </S.ProfileFlex>
                </td>
              </tr>
            ))}
          </S.ProfileInfoTable>
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
