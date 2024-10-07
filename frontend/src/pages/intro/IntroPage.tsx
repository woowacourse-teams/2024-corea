import { useEffect, useRef, useState } from "react";
import IntroNavigationBar from "@/components/intro/IntroNavigationBar";
import * as S from "@/pages/intro/IntroPage.style";

const IntroPage = () => {
  const [currentSection, setCurrentSection] = useState(0);
  const layoutRef = useRef<HTMLDivElement | null>(null);

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
      <S.ContentLayout>섹션1</S.ContentLayout>
      <S.ContentLayout>섹션2</S.ContentLayout>
      <S.ContentLayout>섹션3</S.ContentLayout>
      <IntroNavigationBar
        totalSections={3}
        currentSection={currentSection}
        onDotClick={handleDotClick}
      />
    </S.Layout>
  );
};

export default IntroPage;
