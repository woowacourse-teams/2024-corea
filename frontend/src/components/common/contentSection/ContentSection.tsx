import { ReactNode } from "react";
import Button, { ButtonProps } from "@/components/common/button/Button";
import * as S from "@/components/common/contentSection/ContentSection.style";
import Icon from "@/components/common/icon/Icon";
import IconKind from "@/@types/icon";

interface ContentSectionButton extends ButtonProps {
  label?: string;
  icon?: IconKind;
}

interface ContentSectionProps {
  title: string;
  children?: ReactNode;
  button?: ContentSectionButton | undefined;
}

const ContentSection = ({ title, children, button }: ContentSectionProps) => {
  const buttonSize = button?.icon ? "xSmall" : "small";

  return (
    <S.ContentSectionContainer>
      <S.ContentSectionHeader>
        <S.ContentSectionTitle>{title}</S.ContentSectionTitle>
        {button && (
          <Button onClick={button.onClick} size={buttonSize}>
            {button.icon && <Icon kind={button.icon} size={20} />}
            {button.label}
          </Button>
        )}
      </S.ContentSectionHeader>
      {children}
    </S.ContentSectionContainer>
  );
};

export default ContentSection;
