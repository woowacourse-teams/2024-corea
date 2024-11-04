import { ReactNode } from "react";
import Button, { ButtonProps } from "@/components/common/button/Button";
import * as S from "@/components/common/contentSection/ContentSection.style";

interface ContentSectionButton extends ButtonProps {
  label: string;
}

interface ContentSectionProps {
  title: string;
  subtitle?: string;
  children?: ReactNode;
  button?: ContentSectionButton | undefined;
}

const ContentSection = ({ title, subtitle, children, button }: ContentSectionProps) => {
  return (
    <S.ContentSectionContainer>
      <S.ContentSectionHeader>
        <S.TitleContainer>
          <S.ContentSectionTitle>{title}</S.ContentSectionTitle>
          <S.ContentSectionSubtitle>{subtitle}</S.ContentSectionSubtitle>
        </S.TitleContainer>

        {button && (
          <Button onClick={button.onClick} size="small">
            {button.label}
          </Button>
        )}
      </S.ContentSectionHeader>
      {children}
    </S.ContentSectionContainer>
  );
};

export default ContentSection;
