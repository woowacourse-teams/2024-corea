import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "@/components/common/button/Button";
import IntroNavigationBar from "@/components/intro/IntroNavigationBar";
import * as S from "@/pages/intro/IntroPage.style";
import {
  puzzle_with_people_color,
  question_with_color,
  step2_pic,
  step3_pic,
  step5_pic,
  step11_pic,
  step12_pic,
  step41_pic,
  step42_pic,
} from "@/assets/index";

const IntroPage = () => {
  const [currentSection, setCurrentSection] = useState(0);
  const layoutRef = useRef<HTMLDivElement | null>(null);
  const navigate = useNavigate();

  const handleScroll = () => {
    if (layoutRef.current) {
      const scrollPosition = layoutRef.current.scrollTop;
      const windowHeight = window.innerHeight;
      const currentSectionIndex = Math.round(scrollPosition / windowHeight);
      setCurrentSection(currentSectionIndex);
    }
  };

  useEffect(() => {
    const layout = layoutRef.current;
    if (layout) {
      layout.addEventListener("scroll", handleScroll);
      return () => layout.removeEventListener("scroll", handleScroll);
    }
  }, []);

  const handleDotClick = (index: number) => {
    if (layoutRef.current) {
      layoutRef.current.scrollTo({
        top: index * window.innerHeight,
        behavior: "smooth",
      });
    }
  };

  return (
    <S.Layout ref={layoutRef}>
      <S.ContentLayout>
        <S.ImgWrapper src={puzzle_with_people_color} alt="CoReA 로고 이미지" />
        <S.TextContainer>
          <p className="main">CoReA</p>
          <p className="sub">코드 리뷰 매칭 플랫폼</p>
          <p className="sub">
            CoReA로 완성하는 개발 성장의 퍼즐:
            <span className="desktop-only"> 코드, 리뷰, 그리고 당신</span>
          </p>
          <p className="sub mobile-break">코드, 리뷰, 그리고 당신</p>
        </S.TextContainer>

        <S.ButtonWrapper>
          <Button onClick={() => navigate("/")} size="large">
            CoReA 이용하기
          </Button>
        </S.ButtonWrapper>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSectionColumn>
          <S.ChatBubble className="one">{`"남들은 내 코드를 어떻게 생각하고 있을까?"`}</S.ChatBubble>
          <S.ChatBubble className="two">{`"좋은 코드에 대한 평가 기준은 없을까?"`}</S.ChatBubble>
          <S.ChatBubble className="three">{`"코드리뷰하면 좋다던데... 사람은 어떻게 구하지?"`}</S.ChatBubble>
          <S.ChatBubble className="four">{`"내가 지금 제대로 공부하고 있는 건가?"`}</S.ChatBubble>
          <S.ImgWrapper src={question_with_color} style={{ marginTop: "2rem" }} />
        </S.ContentSectionColumn>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.ImgSection src={step11_pic} />
          <S.TextSection>
            <p className="step">STEP 1-1</p>
            <p className="main">방 참여</p>
            <p className="sub">원하는 미션을 선택하고 방 세부 정보를 확인한 후 참여하세요.</p>
          </S.TextSection>
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.TextSectionRight>
            <p className="step">STEP 1-2</p>
            <p className="main">방 생성</p>
            <p className="sub">원하는 미션이 없다면 직접 만들어보세요.</p>
          </S.TextSectionRight>
          <S.ImgSection src={step12_pic} />
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.ImgSection src={step2_pic} />
          <S.TextSection>
            <p className="step">STEP 2</p>
            <p className="main">리뷰어, 리뷰이 매칭</p>
            <p className="sub">모집 마감 후 리뷰어와 리뷰이가 자동으로 매칭돼요.</p>
          </S.TextSection>
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.TextSectionRight>
            <p className="step">STEP 3</p>
            <p className="main">코드리뷰</p>
            <p className="sub">매칭된 리뷰이의 PR 링크에서 깃허브 코드리뷰를 진행해요.</p>
          </S.TextSectionRight>
          <S.ImgSection src={step3_pic} />
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.ImgSection src={step41_pic} />
          <S.TextSection>
            <p className="step">STEP 4-1</p>
            <p className="main">피드백 작성 for 리뷰이</p>
            <p className="sub">
              코드리뷰를 마치고 코드리뷰 완료 버튼을 클릭하면 피드백을 작성할 수 있어요.
            </p>
            <p className="sub">리뷰어의 코드리뷰에 대한 피드백을 남겨주세요.</p>
          </S.TextSection>
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.TextSectionRight>
            <p className="step">STEP 4-2</p>
            <p className="main">피드백 작성 for 리뷰어</p>
            <p className="sub">리뷰어가 코드리뷰를 완료하면 피드백 작성 버튼이 활성화돼요.</p>
            <p className="sub">버튼을 클릭하여 상대방의 리뷰에 대한 피드백을 남겨주세요.</p>
          </S.TextSectionRight>
          <S.ImgSection src={step42_pic} />
        </S.ContentSection>
      </S.ContentLayout>

      <S.ContentLayout>
        <S.ContentSection>
          <S.ImgSection src={step5_pic} />
          <S.TextSection>
            <p className="step">STEP 5</p>
            <p className="main">피드백 확인</p>
            <p className="sub">피드백 모아보기에서 주고받은 모든 피드백을 확인할 수 있어요.</p>
          </S.TextSection>
        </S.ContentSection>
        <S.ButtonWrapper style={{ position: "absolute", bottom: "10%" }}>
          <Button onClick={() => navigate("/")} size="large">
            CoReA 서비스 바로가기
          </Button>
        </S.ButtonWrapper>
      </S.ContentLayout>

      <IntroNavigationBar
        totalSections={9}
        currentSection={currentSection}
        onDotClick={handleDotClick}
      />
    </S.Layout>
  );
};

export default IntroPage;
