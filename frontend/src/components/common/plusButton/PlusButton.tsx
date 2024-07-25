import Icon from "../icon/Icon";
import React from "react";
import * as S from "@/components/common/plusButton/PlusButton.style";

interface PlusButtonProps {
  onClick: () => void;
}

const PlusButton = ({ onClick }: PlusButtonProps) => {
  return (
    <S.ButtonContainer onClick={onClick}>
      더보기
      <Icon kind="plus" />
    </S.ButtonContainer>
  );
};

export default PlusButton;
