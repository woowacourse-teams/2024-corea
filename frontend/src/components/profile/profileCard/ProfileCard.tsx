import * as S from "./ProfileCard.style";
import React from "react";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import AttitudeScore from "@/components/profile/attitudeScore/AttitudeScore";

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

const ProfileCard = (profileData: ProfileCardProps) => {
  const userInfo = [
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
              <tr>
                <td>{info.title}</td>
                <td>{info.value.toLocaleString()}개</td>
              </tr>
            ))}
            <tr>
              <td>평균 평점</td>
              <td>
                <S.ProfileFlex>
                  <Icon kind="star" /> {profileData.averageRating}
                </S.ProfileFlex>
              </td>
            </tr>
          </S.ProfileInfoTable>
        </S.ProfileInfoWrapper>
        <S.KeywordContainer>
          {profileData.feedbackKeywords.map((keyword) => (
            <S.KeywordWrapper>
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
