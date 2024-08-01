import ProfileDropdown from "./ProfileDropdown";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import * as S from "@/components/common/header/Header.style";

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

  return (
    <S.HeaderContainer>
      <S.HeaderLogo onClick={() => handlePage("/", "")}>CoReA</S.HeaderLogo>
      <S.HeaderNavBarContainer>
        <S.HeaderList>
          {headerItems.map((item) => (
            <S.HeaderItem
              key={item.name}
              onClick={() => handlePage(item.path, item.name)}
              className={isSelect === item.name ? "selected" : ""}
            >
              {item.name}
            </S.HeaderItem>
          ))}
        </S.HeaderList>
        <ProfileDropdown></ProfileDropdown>
      </S.HeaderNavBarContainer>
    </S.HeaderContainer>
  );
};

export default Header;
