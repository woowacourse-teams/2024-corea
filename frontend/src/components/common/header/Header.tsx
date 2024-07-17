import * as S from "@/components/common/header/Header.style";

const Header = () => {
  return (
    <S.HeaderContainer>
      <div>CoReA</div>
      <ul>
        <li>가이드</li>
        <li>랭킹</li>
        <li>마이페이지</li>
      </ul>
    </S.HeaderContainer>
  );
};

export default Header;
