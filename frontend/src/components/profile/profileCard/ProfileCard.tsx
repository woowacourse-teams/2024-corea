import * as S from "./ProfileCard.style";
import React from "react";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import AttitudeScore from "@/components/profile/attitudeScore/AttitudeScore";
import IconKind from "@/@types/icon";

interface ProfileCardProps {
  profileImage: string;
  nickname: string;
  receivedReviewCount: number;
  givenReviewCount: number;
  feedbackCount: number;
  averageRating: number;
  feedbackKeywords: string[];
  attitudeScore: number;
}

interface UserInfo {
  title: string;
  value: number;
  iconKind: IconKind;
}

const ProfileCard = (profileData: ProfileCardProps) => {
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
      <S.ProfileTitle>나의 프로필</S.ProfileTitle>
      <S.ProfileCardWrapper>
        <S.ProfileWrapper>
          <Profile imgSrc={profileData.profileImage} size={100} />
          <S.ProfileNickname>{profileData.nickname}</S.ProfileNickname>
        </S.ProfileWrapper>
        <S.ProfileInfoWrapper>
          <S.ProfileInfoTable>
            {userInfo.map((info) => (
              <tr key={info.title}>
                <td>{info.title}</td>
                <td>
                  <S.ProfileFlex>
                    <Icon kind={info.iconKind} /> {"   " + info.value.toLocaleString()}
                    {info.iconKind === "pencil" && "개"}
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
        <S.AttitudeScoreText>매너잔디</S.AttitudeScoreText>
        <AttitudeScore score={profileData.attitudeScore} />
      </S.AttitudeScoreWrapper>
    </S.ProfileCardContainer>
  );
};

export default ProfileCard;
