import Profile from "../profile/Profile";
import { Splitter } from "../splitter/Splitter";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import * as S from "@/components/common/header/ProfileDropdown.style";
import { blankProfile } from "@/assets";

const dropdownItems = [
  {
    name: "마이페이지",
    path: "/profile",
  },
  {
    name: "피드백 모아보기",
    path: "/feedback",
  },
];

const ProfileDropdown = () => {
  const userInfo = JSON.parse(localStorage.getItem("userInfo") || "{}");
  const profileImage = userInfo?.avatar_url || blankProfile;
  const navigate = useNavigate();
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();
  const { postLogoutMutation } = useMutateAuth();

  const handleProfileClick = (event: React.MouseEvent) => {
    event.preventDefault();
    handleToggleDropdown();
  };

  const handleDropdownItemClick = (path: string) => {
    handleToggleDropdown();
    navigate(path);
  };

  const handleLogoutClick = () => {
    postLogoutMutation.mutate();
    handleToggleDropdown();
  };

  return (
    <S.ProfileContainer ref={dropdownRef}>
      <Profile imgSrc={profileImage} onClick={handleProfileClick} />

      {isDropdownOpen && (
        <S.DropdownMenu>
          <S.ProfileWrapper>
            <Profile imgSrc={profileImage} />
            <S.ProfileInfo>{userInfo.name || "이름을 설정해주세요"}</S.ProfileInfo>
          </S.ProfileWrapper>

          <FocusTrap onEscapeFocusTrap={() => handleToggleDropdown()}>
            <S.DropdownItemWrapper>
              {dropdownItems.map((item) => (
                <S.DropdownItem
                  key={item.name}
                  onClick={() => handleDropdownItemClick(item.path)}
                  tabIndex={0}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") handleDropdownItemClick(item.path);
                  }}
                >
                  {item.name}
                </S.DropdownItem>
              ))}
              <Splitter size={1} color="grey1" />
              <S.DropdownItem
                onClick={handleLogoutClick}
                tabIndex={0}
                onKeyDown={(e) => {
                  if (e.key === "Enter") handleLogoutClick();
                }}
              >
                로그아웃
              </S.DropdownItem>
            </S.DropdownItemWrapper>
          </FocusTrap>
        </S.DropdownMenu>
      )}
    </S.ProfileContainer>
  );
};

export default ProfileDropdown;
