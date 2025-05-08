import ProfileDropdown from "../ProfileDropdown/ProfileDropdown";
import * as S from "./DesktopHeader.style";
import { Link, useLocation } from "react-router-dom";
import { MENU_ITEMS, PUBLIC_MENU_ITEMS } from "@/constants/headerMenu";

const DesktopHeader = () => {
  const { pathname } = useLocation();
  const isLoggedIn = !!localStorage.getItem("accessToken");

  const renderMenuItem = (item: (typeof MENU_ITEMS)[keyof typeof MENU_ITEMS]) => {
    const isSelected = item.type === "link" && item.path === pathname;

    switch (item.type) {
      case "link":
        return (
          <S.HeaderItem key={item.name} className={isSelected ? "selected" : ""}>
            <Link to={item.path}>{item.name}</Link>
          </S.HeaderItem>
        );
      case "external":
        return (
          <S.HeaderItem key={item.name}>
            <a
              href={item.href}
              target={item.name === "로그인" ? "_self" : "_blank"}
              rel="noreferrer"
            >
              {item.name}
            </a>
          </S.HeaderItem>
        );
      default:
        return null;
    }
  };

  return (
    <S.DesktopNavBarContainer>
      {PUBLIC_MENU_ITEMS.map(renderMenuItem)}

      {isLoggedIn ? <ProfileDropdown /> : renderMenuItem(MENU_ITEMS.LOGIN)}
    </S.DesktopNavBarContainer>
  );
};

export default DesktopHeader;
