import Content from "./Content";
import * as S from "./Content.style";
import React from "react";

const MainContentLayout = () => {
  return (
    <S.ContentContainer>
      <Content />
    </S.ContentContainer>
  );
};

export default MainContentLayout;
