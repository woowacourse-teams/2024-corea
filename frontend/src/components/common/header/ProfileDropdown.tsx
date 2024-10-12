import Profile from "../profile/Profile";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import * as S from "@/components/common/header/ProfileDropdown.style";

const dropdownItems = [
  {
    name: "피드백 모아보기",
    path: "/feedback",
  },
  {
    name: "마이페이지",
    path: "/profile",
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

      <S.DropdownMenu show={isDropdownOpen}>
        <S.ProfileWrapper>
          <Profile imgSrc={userInfo.avatar_url} />
          <S.ProfileInfo>
            <strong>{userInfo.name}</strong>
            <span>{userInfo.email !== "" ? userInfo.email : "email 비공개"}</span>
          </S.ProfileInfo>
        </S.ProfileWrapper>

        <S.DropdownItemWrapper>
          {dropdownItems.map((item) => (
            <S.DropdownItem key={item.name} onClick={() => handleDropdownItemClick(item.path)}>
              {item.name}
            </S.DropdownItem>
          ))}
          <S.DropdownItem onClick={handleLogoutClick}>로그아웃</S.DropdownItem>
        </S.DropdownItemWrapper>
      </S.DropdownMenu>
    </S.ProfileContainer>
  );
};

export default ProfileDropdown;
