import CyclingClasses from "./CyclingClasses";
import * as S from "@/components/main/banner/Banner.style";
import Cloud from "@/assets/cloud.png";

const firstItems = ["코드", "개발자", "리뷰", "리뷰어", "리뷰이"];
const secondItems = ["리뷰", "코드", "리뷰어", "개발자", "리뷰이"];

const Banner = () => {
  return (
    <S.BannerContainer>
      <S.Sunlight className="sun" />
      <S.Sunlight className="sunlight-1" />
      <S.Sunlight className="sunlight-2" />
      <S.Sunlight className="sunlight-3" />
      <S.Sunlight className="sunlight-4" />
      <S.Sunlight className="sunlight-5" />

      <S.Cloud className="cloud-1" src={Cloud} alt="구름" />
      <S.Cloud className="cloud-2" src={Cloud} alt="구름" />
      <S.Cloud className="cloud-3" src={Cloud} alt="구름" />

      <S.BannerTextContainer>
        <CyclingClasses items={firstItems} speed={4100} />
        <span>와</span>
        <CyclingClasses items={secondItems} speed={2700} />
        <span>를 연결합니다</span>
      </S.BannerTextContainer>
    </S.BannerContainer>
  );
};

export default Banner;
