import { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import { Link } from "react-router-dom";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/header/SideNavBar.style";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import { githubAuthUrl } from "@/config/githubAuthUrl";

const portalElement = document.getElementById("sideNavBar") as HTMLElement;

const loggedInMenuItems = [
  {
    name: "마이페이지",
    path: "/profile",
  },
  {
    name: "피드백 모아보기",
    path: "/feedback",
  },
  {
    name: "코드리뷰가이드",
    path: "/guide",
  },
];

const commonMenuItems = [
  {
    name: "코드리뷰가이드",
    path: "/guide",
  },
];

interface SideNavBarProps {
  isOpen: boolean;
  onClose: () => void;
  isLoggedIn: boolean;
}

const SideNavBar = ({ isOpen, onClose, isLoggedIn }: SideNavBarProps) => {
  const [isClosing, setIsClosing] = useState(false);
  const userInfo = JSON.parse(localStorage.getItem("userInfo") || "{}");
  const { postLogoutMutation } = useMutateAuth();

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
      setIsClosing(false);
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);

  const handleClose = () => {
    setIsClosing(true);
    setTimeout(() => {
      onClose();
      setIsClosing(false);
    }, 500);
  };

  const handleLogoutClick = () => {
    postLogoutMutation.mutate();
    onClose();
  };

  const sideNavContent = (
    <S.SideNavBarContainer $isOpen={isOpen} $isClosing={isClosing}>
      {isLoggedIn ? (
        <>
          <S.TopSection>
            <Button onClick={handleClose} size="xSmall">
              <Icon kind="close" size="2.4rem" />
            </Button>
            <S.ProfileWrapper>
              <Profile imgSrc={userInfo.avatar_url} size={44} />
              <S.ProfileInfo>
                <strong>{userInfo.name}</strong>
                <span>{userInfo.email !== "" ? userInfo.email : "email 비공개"}</span>
              </S.ProfileInfo>
            </S.ProfileWrapper>
            <S.LogoutButton
              onClick={handleLogoutClick}
              tabIndex={0}
              onKeyDown={(e) => {
                if (e.key === "Enter") handleLogoutClick();
              }}
            >
              로그아웃
            </S.LogoutButton>
          </S.TopSection>

          <S.NavItems>
            {loggedInMenuItems.map((item) => (
              <S.NavItem key={item.name}>
                <Link to={item.path} onClick={onClose}>
                  {item.name}
                </Link>
              </S.NavItem>
            ))}
          </S.NavItems>
        </>
      ) : (
        <>
          <S.TopSection>
            <Button onClick={onClose} size="xSmall">
              <Icon kind="close" size="2.4rem" />
            </Button>
            <S.NavItem>
              <a href={githubAuthUrl} onClick={onClose}>
                로그인
              </a>
            </S.NavItem>
          </S.TopSection>

          <S.NavItems>
            {commonMenuItems.map((item) => (
              <S.NavItem key={item.name}>
                <Link to={item.path} onClick={onClose}>
                  {item.name}
                </Link>
              </S.NavItem>
            ))}
          </S.NavItems>
        </>
      )}
    </S.SideNavBarContainer>
  );

  return createPortal(
    <>
      <S.BackDrop $isOpen={isOpen} onClick={handleClose} />
      {sideNavContent}
    </>,
    portalElement,
  );
};

export default SideNavBar;
