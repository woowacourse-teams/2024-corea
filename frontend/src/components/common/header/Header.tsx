import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/header/Header.style";
import ProfileDropdown from "@/components/common/header/ProfileDropdown";
import SideNavBar from "@/components/common/header/SideNavBar";
import Icon from "@/components/common/icon/Icon";
import { githubAuthUrl } from "@/config/githubAuthUrl";

const headerItems = [
  {
    name: "코드리뷰가이드",
    path: "/guide",
  },
  // {
  //   name: "랭킹",
  //   path: "/ranking",
  // },
];

const Header = () => {
  const { pathname } = useLocation();
  const [isSelect, setIsSelect] = useState("");
  const [isSideNavOpen, setIsSideNavOpen] = useState(false);
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const isMain = pathname === "/";

  useEffect(() => {
    const currentItem = headerItems.find((item) => item.path === pathname);
    if (currentItem) {
      setIsSelect(currentItem.name);
    } else {
      setIsSelect("");
    }
  }, [pathname]);

  const toggleSideNav = () => {
    setIsSideNavOpen(!isSideNavOpen);
  };

  return (
    <S.HeaderContainer $isMain={isMain}>
      <S.HeaderLogo>
        <Link to="/">
          <span>CoReA</span>
        </Link>
      </S.HeaderLogo>

      <S.HeaderNavBarContainer>
        {headerItems.map((item) => (
          <S.HeaderItem
            $isMain={isMain}
            key={item.name}
            className={isSelect === item.name ? "selected" : ""}
          >
            <Link to={item.path}>{item.name}</Link>
          </S.HeaderItem>
        ))}
        {isLoggedIn ? (
          <ProfileDropdown />
        ) : (
          <S.HeaderItem $isMain={isMain}>
            <a href={githubAuthUrl}>로그인</a>
          </S.HeaderItem>
        )}
      </S.HeaderNavBarContainer>

      <S.SideNavBarContainer>
        <Button onClick={toggleSideNav} size="xSmall" variant="default">
          <Icon kind="menu" size="2.6rem" />
        </Button>
      </S.SideNavBarContainer>
      <SideNavBar isOpen={isSideNavOpen} onClose={toggleSideNav} isLoggedIn={isLoggedIn} />
    </S.HeaderContainer>
  );
};

export default Header;
