import AlarmButton from "./AlarmButton/AlarmButton";
import DesktopHeader from "./DesktopHeader/DesktopHeader";
import * as S from "./Header.style";
import MobileHeader from "./MobileHeader/MobileHeader";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { SCREEN } from "@/constants/media";

const Header = () => {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= SCREEN.SMALL);

  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth <= SCREEN.SMALL);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <S.HeaderContainer $isMain={true}>
      <S.HeaderLogo>
        <Link to="/">
          <span>CoReA</span>
        </Link>
      </S.HeaderLogo>

      <S.HeaderNavBarContainer>
        <AlarmButton />

        {isMobile ? <MobileHeader /> : <DesktopHeader />}
      </S.HeaderNavBarContainer>
    </S.HeaderContainer>
  );
};

export default Header;
