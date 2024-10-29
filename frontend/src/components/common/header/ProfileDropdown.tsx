import Profile from "../profile/Profile";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import * as S from "@/components/common/header/ProfileDropdown.style";

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
      <Profile imgSrc={userInfo.avatar_url} onClick={handleProfileClick} />

      {isDropdownOpen && (
        <S.DropdownMenu>
          <S.ProfileWrapper>
            <Profile imgSrc={userInfo.avatar_url} />
            <S.ProfileInfo>{userInfo.name}</S.ProfileInfo>
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
