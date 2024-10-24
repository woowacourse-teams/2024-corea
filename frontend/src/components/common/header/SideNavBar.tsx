import { useEffect, useRef, useState } from "react";
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
    name: "서비스 소개",
    path: "/intro",
  },
  {
    name: "코드리뷰가이드",
    path: "/guide",
  },
];

const commonMenuItems = [
  {
    name: "서비스 소개",
    path: "/intro",
  },
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
  const previousFocusedElement = useRef<HTMLElement | null>(null);
  const firstChildRef = useRef<HTMLButtonElement | null>(null);

  useEffect(() => {
    if (isOpen) {
      previousFocusedElement.current = document.activeElement as HTMLElement;
      document.body.style.overflow = "hidden";
      firstChildRef.current?.focus();
    } else {
      document.body.style.overflow = "auto";
    }

    [...document.body.children].forEach((element) => {
      if (element.id === "toast") return;

      if (element.id === "sideNavBar") {
        element.removeAttribute("aria-hidden");
        return;
      }

      if (isOpen) {
        element.setAttribute("aria-hidden", "true");
        return;
      }

      element.removeAttribute("aria-hidden");
    });

    return () => {
      previousFocusedElement.current?.focus();
      document.getElementById("root")?.setAttribute("aria-hidden", "false");
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);

  if (!isOpen) return null;

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
            <Button
              onClick={handleClose}
              size="xSmall"
              style={{ background: "transparent", color: "black" }}
              ref={firstChildRef}
            >
              <Icon kind="close" size="2.4rem" />
            </Button>
            <S.ProfileWrapper>
              <Profile imgSrc={userInfo.avatar_url} size={44} />
              <S.ProfileInfo>{userInfo.name}</S.ProfileInfo>
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
            <S.NavItem>
              <a
                href="https://docs.google.com/forms/d/e/1FAIpQLSeuuEJ6oCnRi5AYjlKhWt-H5EEibZPIRLA4lY-5oqZpC2b0SA/viewform"
                target="_blank"
                rel="noreferrer"
              >
                문의
              </a>
            </S.NavItem>
          </S.NavItems>
        </>
      ) : (
        <>
          <S.TopSection>
            <Button
              onClick={onClose}
              size="xSmall"
              style={{ background: "transparent", color: "black" }}
              ref={firstChildRef}
            >
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
            <S.NavItem>
              <a
                href="https://docs.google.com/forms/d/e/1FAIpQLSeuuEJ6oCnRi5AYjlKhWt-H5EEibZPIRLA4lY-5oqZpC2b0SA/viewform"
                target="_blank"
                rel="noreferrer"
              >
                문의
              </a>
            </S.NavItem>
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
