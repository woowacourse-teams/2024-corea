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

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownToggle onClick={handleToggleDropdown}>
        {dropdownItems.find((item) => item.value === selectedCategory)?.text || "선택해주세요"}
        <Icon kind={isDropdownOpen ? "arrowDropUp" : "arrowDropDown"} />
      </S.DropdownToggle>

      {isDropdownOpen && (
        <S.DropdownMenu>
          <S.DropdownItemWrapper>
            {dropdownItems.map((item) => (
              <S.DropdownItem key={item.value} onClick={() => handleDropdownItemClick(item.value)}>
                <span>{item.text}</span>
              </S.DropdownItem>
            ))}
          </S.DropdownItemWrapper>
        </S.DropdownMenu>
      )}
    </S.DropdownContainer>
  );
};

export default Dropdown;
