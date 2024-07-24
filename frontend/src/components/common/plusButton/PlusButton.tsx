import Icon from "../icon/Icon";
import React from "react";
import * as S from "@/components/common/plusButton/PlusButton.style";

const PlusButton = () => {
  return (
    <S.ButtonContainer>
      더보기
      <Icon kind="plus" />
    </S.ButtonContainer>
  );
};

export default PlusButton;
