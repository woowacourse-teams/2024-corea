import { ReactNode } from "react";
import * as S from "@/components/common/contentSection/ContentSection.style";

interface ContentSectionProps {
  title: string;
  children?: ReactNode;
}

const ContentSection = ({ title, children }: ContentSectionProps) => {
  return (
    <S.ContentSectionContainer>
      <S.ContentSectionTitle>{title}</S.ContentSectionTitle>
      {children}
    </S.ContentSectionContainer>
  );
};

export default ContentSection;
