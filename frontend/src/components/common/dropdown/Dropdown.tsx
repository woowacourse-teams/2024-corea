import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/dropdown/Dropdown.style";
import Icon from "@/components/common/icon/Icon";

export interface DropdownItem {
  text: string;
  value: string;
}

interface DropdownProps {
  dropdownItems: DropdownItem[];
  selectedCategory: string;
  onSelectCategory: (category: string) => void;
}

const Dropdown = ({ dropdownItems, onSelectCategory, selectedCategory }: DropdownProps) => {
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();

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
        {isDropdownOpen ? <Icon kind="arrowDropUp" /> : <Icon kind="arrowDropDown" />}
      </S.DropdownToggle>
      <S.DropdownMenu show={isDropdownOpen}>
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

export default Dropdown;
