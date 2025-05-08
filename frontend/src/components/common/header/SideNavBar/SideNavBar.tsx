import { useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import { Link } from "react-router-dom";
import useMutateAuth from "@/hooks/mutations/useMutateAuth";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/header/SideNavBar/SideNavBar.style";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import { MENU_ITEMS, PRIVATE_MENU_ITEMS, PUBLIC_MENU_ITEMS } from "@/constants/headerMenu";

const portalElement = document.getElementById("sideNavBar") as HTMLElement;

interface SideNavBarProps {
  isOpen: boolean;
  onClose: () => void;
}

const SideNavBar = ({ isOpen, onClose }: SideNavBarProps) => {
  const [isClosing, setIsClosing] = useState(false);
  const userInfo = JSON.parse(localStorage.getItem("userInfo") || "{}");
  const isLoggedIn = !!localStorage.getItem("accessToken");
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
      } else {
        element.removeAttribute("aria-hidden");
      }
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

  const menuItems = isLoggedIn ? [...PRIVATE_MENU_ITEMS, ...PUBLIC_MENU_ITEMS] : PUBLIC_MENU_ITEMS;

  const renderMenuItem = (item: (typeof MENU_ITEMS)[keyof typeof MENU_ITEMS]) => {
    switch (item.type) {
      case "link":
        return (
          <S.NavItem key={item.name}>
            <Link to={item.path} onClick={onClose}>
              {item.name}
            </Link>
          </S.NavItem>
        );
      case "external":
        return (
          <S.NavItem key={item.name}>
            <a
              href={item.href}
              target={item.name === "로그인" ? "_self" : "_blank"}
              rel="noreferrer"
            >
              {item.name}
            </a>
          </S.NavItem>
        );
      case "action":
        return (
          <S.NavItem
            key={item.name}
            onClick={handleLogoutClick}
            onKeyDown={(e) => e.key === "Enter" && handleLogoutClick()}
            tabIndex={0}
          >
            {item.name}
          </S.NavItem>
        );
    }
  };

  const sideNavContent = (
    <S.SideNavBarContainer $isOpen={isOpen} $isClosing={isClosing}>
      <S.TopSection>
        <Button
          onClick={handleClose}
          size="xSmall"
          style={{ background: "transparent", color: "black", border: "none" }}
          ref={firstChildRef}
        >
          <Icon kind="close" size="2.4rem" />
        </Button>

        {isLoggedIn ? (
          <>
            <S.ProfileWrapper>
              <Profile imgSrc={userInfo.avatar_url} size={44} />
              <S.ProfileInfo>{userInfo.name}</S.ProfileInfo>
            </S.ProfileWrapper>
            {renderMenuItem(MENU_ITEMS.LOGOUT)}
          </>
        ) : (
          <>{renderMenuItem(MENU_ITEMS.LOGIN)}</>
        )}
      </S.TopSection>

      <S.NavItems>{menuItems.map(renderMenuItem)}</S.NavItems>
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
