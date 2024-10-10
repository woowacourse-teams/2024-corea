import CyclingClasses from "./CyclingClasses";
import * as S from "@/components/main/banner/Banner.style";
import { mainLogo } from "@/assets";

const firstItems = ["코드", "개발자", "리뷰", "리뷰어", "리뷰이"];
const secondItems = ["리뷰", "코드", "리뷰어", "개발자", "리뷰이"];

const Banner = () => {
  return (
    <S.BannerContainer>
      <S.ImageWrapper>
        <img src={mainLogo} />
      </S.ImageWrapper>

      <S.BannerTextContainer>
        <S.BannerText>
          <CyclingClasses items={firstItems} speed={11100} />
          <span>와</span>
          <CyclingClasses items={secondItems} speed={3700} />
          <span>를 연결합니다</span>
        </S.BannerText>
      </S.BannerTextContainer>
    </S.BannerContainer>
  );
};

export default Banner;
