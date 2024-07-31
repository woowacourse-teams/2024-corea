import * as S from "./Profile.style";
import { ButtonHTMLAttributes } from "react";
import React from "react";

interface ProfileProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  imgSrc: string;
}

const Profile = ({ imgSrc, ...rest }: ProfileProps) => {
  return (
    <S.ProfileContainer {...rest}>
      <S.ProfileImg src={imgSrc} alt="유저 프로필 이미지" />
    </S.ProfileContainer>
  );
};

export default Profile;
