import Profile from "../../profile/Profile";
import { Splitter } from "../../splitter/Splitter";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import * as S from "@/components/common/header/ProfileDropdown/ProfileDropdown.style";
import { blankProfile } from "@/assets";
import { MENU_ITEMS, PRIVATE_MENU_ITEMS } from "@/constants/headerMenu";

const ProfileDropdown = () => {
  const userInfo = JSON.parse(localStorage.getItem("userInfo") || "{}");
  const profileImage = userInfo?.avatar_url || blankProfile;
  const navigate = useNavigate();
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();
  const { postLogoutMutation } = useMutateAuth();

  const handleDropdownItemClick = (item: (typeof MENU_ITEMS)[keyof typeof MENU_ITEMS]) => {
    handleToggleDropdown();

    if (item.type === "link") {
      navigate(item.path);
    } else if (item.type === "action" && item.key === "logout") {
      postLogoutMutation.mutate();
    }
  };

  const renderMenuItem = (item: (typeof MENU_ITEMS)[keyof typeof MENU_ITEMS]) => (
    <S.DropdownItem
      key={item.name}
      onClick={() => handleDropdownItemClick(item)}
      tabIndex={0}
      onKeyDown={(e) => {
        if (e.key === "Enter") handleDropdownItemClick(item);
      }}
    >
      {item.name}
    </S.DropdownItem>
  );

  return (
    <S.ProfileContainer ref={dropdownRef}>
      <Profile imgSrc={profileImage} onClick={handleToggleDropdown} />

      {isDropdownOpen && (
        <S.DropdownMenu>
          <S.ProfileWrapper>
            <Profile imgSrc={profileImage} />
            <S.ProfileInfo>{userInfo.name || "이름을 설정해주세요"}</S.ProfileInfo>
          </S.ProfileWrapper>

          <FocusTrap onEscapeFocusTrap={handleToggleDropdown}>
            <S.DropdownItemWrapper>
              {PRIVATE_MENU_ITEMS.map(renderMenuItem)}

              <Splitter size={1} color="grey1" />

              {renderMenuItem(MENU_ITEMS.LOGOUT)}
            </S.DropdownItemWrapper>
          </FocusTrap>
        </S.DropdownMenu>
      )}
    </S.ProfileContainer>
  );
};

export default ProfileDropdown;
