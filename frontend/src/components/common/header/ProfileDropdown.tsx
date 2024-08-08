import Icon from "../icon/Icon";
import Profile from "../profile/Profile";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import * as S from "@/components/common/header/ProfileDropdown.style";
import profileImage from "@/assets/profile.png";

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
  const navigate = useNavigate();
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();
  const { postLogoutMutation } = useMutateAuth();

  const handleProfileClick = (event: React.MouseEvent) => {
    event.preventDefault();
    handleToggleDropdown();
  };

  const handleDropdownItemClick = (path: string) => {
    handleToggleDropdown();
    navigate(path);
  };

  const handelLogoutClick = () => {
    postLogoutMutation.mutate();
    handleToggleDropdown();
  };

  return (
    <S.ProfileContainer ref={dropdownRef}>
      <Profile imgSrc={profileImage} onClick={handleProfileClick} />

      <S.DropdownMenu show={isOpen}>
        <S.ProfileWrapper>
          <Profile imgSrc={profileImage} />
          <S.ProfileInfo>
            <strong>최진실</strong>
            <span>jinsil@gmail.com</span>
          </S.ProfileInfo>
        </S.ProfileWrapper>

        <S.DropdownItemWrapper>
          {dropdownItems.map((item) => (
            <S.DropdownItem key={item.name} onClick={() => handleDropdownItemClick(item.path)}>
              <Icon kind="info" />
              <span>{item.name}</span>
            </S.DropdownItem>
          ))}
          <hr></hr>
          <S.DropdownItem onClick={handelLogoutClick}>로그아웃</S.DropdownItem>
        </S.DropdownItemWrapper>
      </S.DropdownMenu>
    </S.ProfileContainer>
  );
};

export default ProfileDropdown;
