import ImageWithFallback from "../img/ImageWithFallback";
import { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/profile/Profile.style";
import { blankProfile } from "@/assets";

interface ProfileProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  imgSrc: string;
  size?: number;
}

const Profile = ({ imgSrc, size, ...rest }: ProfileProps) => {
  return (
    <S.ProfileContainer $size={size} {...rest}>
      <S.ProfileImg
        as={ImageWithFallback}
        src={imgSrc}
        $size={size}
        alt="유저 프로필 이미지"
        fallbackSrc={blankProfile}
      />
    </S.ProfileContainer>
  );
};

export default Profile;
