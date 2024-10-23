import * as S from "@/components/intro/IntroNavigationBar.style";

interface IntoNavigationBarProps {
  totalSections: number;
  currentSection: number;
  onDotClick: (index: number) => void;
}

const IntroNavigationBar = ({
  totalSections,
  currentSection,
  onDotClick,
}: IntoNavigationBarProps) => {
  return (
    <S.NavContainer>
      {[...Array(totalSections)].map((_, index) => (
        <S.NavDot key={index} active={currentSection === index} onClick={() => onDotClick(index)} />
      ))}
    </S.NavContainer>
  );
};

export default IntroNavigationBar;
