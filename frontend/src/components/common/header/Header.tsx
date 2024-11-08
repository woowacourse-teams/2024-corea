import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { useFetchAlarmCount } from "@/hooks/queries/useFetchAlarm";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/header/Header.style";
import ProfileDropdown from "@/components/common/header/ProfileDropdown";
import SideNavBar from "@/components/common/header/SideNavBar";
import Icon from "@/components/common/icon/Icon";
import { githubAuthUrl } from "@/config/githubAuthUrl";
import { theme } from "@/styles/theme";

const MOBILE_BREAKPOINT = 639;

const headerItems = [
  {
    name: "소개",
    path: "/intro",
  },
  {
    name: "가이드",
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
  const [isMobile, setIsMobile] = useState(window.innerWidth <= MOBILE_BREAKPOINT);
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const isMain = pathname === "/";

  const { data: alarmCountData } = useFetchAlarmCount();

  useEffect(() => {
    const handleResize = () => {
      const mobile = window.innerWidth <= MOBILE_BREAKPOINT;
      setIsMobile(mobile);

      if (!mobile && isSideNavOpen) {
        setIsSideNavOpen(false);
      }
    };

    window.addEventListener("resize", handleResize);

    handleResize();

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, [isSideNavOpen]);

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
        {isLoggedIn && (
          <Link to="/alarm">
            <S.HeaderItemIcon>
              <Icon kind="notificationBell" size="2.8rem" color={theme.COLOR.grey3} />
              {alarmCountData && alarmCountData.count > 0 && (
                <S.Count>{alarmCountData.count >= 10 ? "9+" : alarmCountData.count}</S.Count>
              )}
            </S.HeaderItemIcon>
          </Link>
        )}

        <S.HeaderItem $isMain={isMain}>
          <a
            href="https://docs.google.com/forms/d/e/1FAIpQLSeuuEJ6oCnRi5AYjlKhWt-H5EEibZPIRLA4lY-5oqZpC2b0SA/viewform"
            target="_blank"
            rel="noreferrer"
          >
            문의
          </a>
        </S.HeaderItem>

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
        {isLoggedIn && (
          <S.HeaderItemIcon>
            <Link to="/alarm">
              <Icon kind="notificationBell" size="2.8rem" color={theme.COLOR.grey3} />
              {alarmCountData && alarmCountData.count > 0 && (
                <S.Count>{alarmCountData.count >= 10 ? "9+" : alarmCountData.count}</S.Count>
              )}
            </Link>
          </S.HeaderItemIcon>
        )}

        <Button onClick={toggleSideNav} size="xSmall" variant="default" aria-label="메뉴">
          <Icon kind="menu" size="2.6rem" />
        </Button>
      </S.SideNavBarContainer>

      {isMobile && (
        <SideNavBar isOpen={isSideNavOpen} onClose={toggleSideNav} isLoggedIn={isLoggedIn} />
      )}
    </S.HeaderContainer>
  );
};

export default Header;
