import * as S from "./Header.style";
import { Link } from "react-router-dom";

interface StaticHeaderProps {
  showShadow?: boolean;
  shouldFixed?: boolean;
}

const StaticHeader = ({ showShadow = true, shouldFixed = false }: StaticHeaderProps) => {
  return (
    <S.HeaderContainer $showShadow={showShadow} $shouldFixed={shouldFixed}>
      <S.HeaderLogo>
        <Link to="/">
          <span>CoReA</span>
        </Link>
      </S.HeaderLogo>
    </S.HeaderContainer>
  );
};

export default StaticHeader;
