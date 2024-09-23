import { ReactNode } from "react";
import Button, { ButtonProps } from "@/components/common/button/Button";
import * as S from "@/components/common/contentSection/ContentSection.style";

interface ContentSectionButton extends ButtonProps {
  label: string;
}

interface ContentSectionProps {
  title: string;
  children?: ReactNode;
  button?: ContentSectionButton | false;
}

const ContentSection = ({ title, children, button }: ContentSectionProps) => {
  return (
    <S.ContentSectionContainer>
      <S.ContentSectionHeader>
        <S.ContentSectionTitle>{title}</S.ContentSectionTitle>
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
