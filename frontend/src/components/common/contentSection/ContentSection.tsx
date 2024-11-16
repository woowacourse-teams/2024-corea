import { ReactNode } from "react";
import * as S from "@/components/common/contentSection/ContentSection.style";

interface ContentSectionProps {
  title: string;
  subtitle?: string;
  controlSection?: ReactNode;
  children?: ReactNode;
}

const ContentSection = ({ title, subtitle, children, controlSection }: ContentSectionProps) => {
  return (
    <S.ContentSectionContainer>
      <S.ContentSectionHeader>
        <S.TitleContainer>
          <S.ContentSectionTitle>{title}</S.ContentSectionTitle>
          <S.ContentSectionSubtitle>{subtitle}</S.ContentSectionSubtitle>
        </S.TitleContainer>

        {controlSection && controlSection}
      </S.ContentSectionHeader>
      {children}
    </S.ContentSectionContainer>
  );
};

export default ContentSection;
