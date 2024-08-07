import * as S from "./Profile.style";
import { ButtonHTMLAttributes } from "react";
import React from "react";

interface ProfileProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  imgSrc: string;
  size?: number;
}

const Profile = ({ imgSrc, size, ...rest }: ProfileProps) => {
  return (
    <S.ProfileContainer $size={size} {...rest}>
      <S.ProfileImg src={imgSrc} $size={size} alt="유저 프로필 이미지" />
    </S.ProfileContainer>
  );
};

export default Profile;
