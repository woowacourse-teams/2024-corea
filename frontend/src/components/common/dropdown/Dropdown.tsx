import { useEffect } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/dropdown/Dropdown.style";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import Icon from "@/components/common/icon/Icon";

export interface DropdownItem {
  text: string;
  value: string;
}

interface DropdownProps {
  name?: string;
  dropdownItems: DropdownItem[];
  selectedCategory: string;
  onSelectCategory: (category: string) => void;
  error?: boolean;
}

const Dropdown = ({
  name = "",
  dropdownItems,
  onSelectCategory,
  selectedCategory,
  error = false,
}: DropdownProps) => {
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleDropdownItemClick = (category: string) => {
    onSelectCategory(category);
    handleToggleDropdown();
  };

  useEffect(() => {
    if (isDropdownOpen && dropdownRef.current) {
      const firstItem = dropdownRef.current.querySelector("[role='option']") as HTMLElement;
      if (firstItem) firstItem.focus();
    }
  }, [isDropdownOpen]);

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownToggle
        onClick={handleToggleDropdown}
        $error={error}
        aria-label={name}
        aria-expanded={isDropdownOpen}
      >
        {dropdownItems.find((item) => item.value === selectedCategory)?.text || "선택해주세요"}
        <Icon kind={isDropdownOpen ? "arrowDropUp" : "arrowDropDown"} />
      </S.DropdownToggle>

      {isDropdownOpen && (
        <S.DropdownMenu role="listbox">
          <FocusTrap onEscapeFocusTrap={() => handleToggleDropdown()}>
            <S.DropdownItemWrapper>
              {dropdownItems.map((item) => (
                <S.DropdownItem
                  key={item.value}
                  onClick={() => handleDropdownItemClick(item.value)}
                  $isSelected={item.value === selectedCategory}
                  role="option"
                  aria-selected={item.value === selectedCategory}
                  tabIndex={0}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") handleDropdownItemClick(item.value);
                  }}
                >
                  <span>{item.text}</span>
                </S.DropdownItem>
              ))}
            </S.DropdownItemWrapper>
          </FocusTrap>
        </S.DropdownMenu>
      )}
    </S.DropdownContainer>
  );
};

export default Dropdown;
