import React from "react";
import IconButton from "@/components/common/iconButton/IconButton";
import * as S from "@/components/common/menuBar/MenuBar.style";
import { allLogo, androidLogo, backendLogo, frontendLogo } from "@/assets";

interface MenuBarProps {
  selectedCategory: string;
  onCategoryClick: (category: string) => void;
}

const MenuBar = ({ selectedCategory, onCategoryClick }: MenuBarProps) => {
  return (
    <S.MenuBarContainer>
      <IconButton
        text="ALL"
        onClick={() => onCategoryClick("all")}
        isSelected={selectedCategory === "all"}
      >
        <S.StyledChildren src={allLogo} alt="all" />
      </IconButton>
      <IconButton
        text="ANDROID"
        onClick={() => onCategoryClick("an")}
        isSelected={selectedCategory === "an"}
      >
        <S.StyledChildren src={androidLogo} alt="android" />
      </IconButton>
      <IconButton
        text="BACKEND"
        onClick={() => onCategoryClick("be")}
        isSelected={selectedCategory === "be"}
      >
        <S.StyledChildren src={backendLogo} alt="backend" />
      </IconButton>
      <IconButton
        text="FRONTEND"
        onClick={() => onCategoryClick("fe")}
        isSelected={selectedCategory === "fe"}
      >
        <S.StyledChildren src={frontendLogo} alt="frontend" />
      </IconButton>
    </S.MenuBarContainer>
  );
};

export default MenuBar;
