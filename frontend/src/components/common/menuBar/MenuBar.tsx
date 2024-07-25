import React from "react";
import IconButton from "@/components/common/iconButton/IconButton";
import * as S from "@/components/common/menuBar/MenuBar.style";
import { allLogo, androidLogo, backendLogo, frontendLogo } from "@/assets";

interface MenuBarProps {
  selectedCategory: string;
  onCategoryClick: (category: string) => void;
}

interface CategoryItem {
  text: string;
  value: string;
  logo: string;
  alt: string;
}

const CATEGORIES: CategoryItem[] = [
  { text: "ALL", value: "all", logo: allLogo, alt: "all" },
  { text: "ANDROID", value: "an", logo: androidLogo, alt: "android" },
  { text: "BACKEND", value: "be", logo: backendLogo, alt: "backend" },
  { text: "FRONTEND", value: "fe", logo: frontendLogo, alt: "frontend" },
];

const MenuBar = ({ selectedCategory, onCategoryClick }: MenuBarProps) => {
  return (
    <S.MenuBarContainer>
      {CATEGORIES.map((category) => (
        <IconButton
          key={category.value}
          text={category.text}
          onClick={() => onCategoryClick(category.value)}
          isSelected={selectedCategory === category.value}
        >
          <S.StyledChildren src={category.logo} alt={category.alt} />
        </IconButton>
      ))}
    </S.MenuBarContainer>
  );
};

export default MenuBar;
