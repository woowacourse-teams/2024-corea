import { useNavigate } from "react-router-dom";
import * as S from "@/components/common/header/Header.style";

const Header = () => {
  const navigate = useNavigate();

  const headerItems = [
    {
      name: "가이드",
      link: "/",
    },
    {
      name: "랭킹",
      link: "/",
    },
    {
      name: "마이",
      link: "/",
    },
  ];

  return (
    <S.HeaderContainer>
      <S.HeaderLogo onClick={() => navigate("/")}>CoReA</S.HeaderLogo>
      <S.HeaderList>
        {headerItems.map((item) => (
          <S.HeaderItem>{item.name}</S.HeaderItem>
        ))}
      </S.HeaderList>
    </S.HeaderContainer>
  );
};

export default Header;
