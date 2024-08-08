import ProfileDropdown from "./ProfileDropdown";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import * as S from "@/components/common/header/Header.style";
import { githubAuthUrl } from "@/config/githubAuthUrl";

const headerItems = [
  {
    name: "가이드",
    path: "/guide",
  },
  {
    name: "랭킹",
    path: "/ranking",
  },
];

const Header = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  const [isSelect, setIsSelect] = useState("");
  const user = localStorage.getItem("accessToken");

  const handlePage = (path: string, name: string) => {
    setIsSelect(name);
    navigate(path);
  };

  useEffect(() => {
    const currentItem = headerItems.find((item) => item.path === pathname);
    if (currentItem) {
      setIsSelect(currentItem.name);
    } else {
      setIsSelect("");
    }
  }, [pathname, headerItems]);

  const handleLogin = () => {
    window.open(githubAuthUrl, "_self");
  };

  return (
    <S.HeaderContainer>
      <S.HeaderLogo onClick={() => handlePage("/", "")}>CoReA</S.HeaderLogo>
      <S.HeaderNavBarContainer>
        {headerItems.map((item) => (
          <S.HeaderItem
            key={item.name}
            onClick={() => handlePage(item.path, item.name)}
            className={isSelect === item.name ? "selected" : ""}
          >
            {item.name}
          </S.HeaderItem>
        ))}
        {user ? <ProfileDropdown /> : <S.HeaderItem onClick={handleLogin}>로그인</S.HeaderItem>}
      </S.HeaderNavBarContainer>
    </S.HeaderContainer>
  );
};

export default Header;
