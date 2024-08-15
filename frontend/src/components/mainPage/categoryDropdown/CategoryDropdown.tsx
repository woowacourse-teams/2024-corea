import useDropdown from "@/hooks/common/useDropdown";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/mainPage/categoryDropdown/CategoryDropdown.style";
import { allLogo, androidLogo, backendLogo, frontendLogo } from "@/assets";

interface DropdownItems {
  text: string;
  value: string;
  logo: string;
}

const dropdownItems: DropdownItems[] = [
  { text: "ALL", value: "all", logo: allLogo },
  { text: "ANDROID", value: "an", logo: androidLogo },
  { text: "BACKEND", value: "be", logo: backendLogo },
  { text: "FRONTEND", value: "fe", logo: frontendLogo },
];

interface CategoryDropdownProps {
  selectedCategory: string;
  onSelectCategory: (category: string) => void;
}

const CategoryDropdown = ({ onSelectCategory, selectedCategory }: CategoryDropdownProps) => {
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleDropdownItemClick = (category: string) => {
    onSelectCategory(category);
    handleToggleDropdown();
  };

  const selectedItem =
    dropdownItems.find((item) => item.value === selectedCategory) || dropdownItems[0];

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownToggle onClick={handleToggleDropdown}>
        {selectedItem.text}
        {isOpen ? <Icon kind="arrowDropUp" /> : <Icon kind="arrowDropDown" />}
      </S.DropdownToggle>
      <S.DropdownMenu show={isOpen}>
        <S.DropdownItemWrapper>
          {dropdownItems.map((item) => (
            <S.DropdownItem key={item.text} onClick={() => handleDropdownItemClick(item.value)}>
              <span>{item.text}</span>
            </S.DropdownItem>
          ))}
        </S.DropdownItemWrapper>
      </S.DropdownMenu>
    </S.DropdownContainer>
  );
};

export default CategoryDropdown;
