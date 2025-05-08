import Icon from "../icon/Icon";
import LocalErrorBoundary from "../localErrorBoundary/LocalErrorBoundary";
import AlarmButton from "./AlarmButton/AlarmButton";
import DesktopHeader from "./DesktopHeader/DesktopHeader";
import * as S from "./Header.style";
import MobileHeader from "./MobileHeader/MobileHeader";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { SCREEN } from "@/constants/media";
import { theme } from "@/styles/theme";

interface HeaderProps {
  showShadow?: boolean;
  shouldFixed?: boolean;
}

const Header = ({ showShadow = true, shouldFixed = false }: HeaderProps) => {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= SCREEN.SMALL);

  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth <= SCREEN.SMALL);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <S.HeaderContainer $showShadow={showShadow} $shouldFixed={shouldFixed}>
      <S.HeaderLogo>
        <Link to="/">
          <span>CoReA</span>
        </Link>
      </S.HeaderLogo>

      <S.HeaderNavBarContainer>
        <LocalErrorBoundary
          resetKeys={[isMobile]}
          fallback={() => <Icon kind="notificationBell" size="2.8rem" color={theme.COLOR.error} />}
        >
          <AlarmButton />
        </LocalErrorBoundary>

        {isMobile ? <MobileHeader /> : <DesktopHeader />}
      </S.HeaderNavBarContainer>
    </S.HeaderContainer>
  );
};

export default React.memo(Header);
