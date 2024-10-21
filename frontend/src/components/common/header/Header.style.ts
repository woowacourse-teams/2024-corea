import styled from "styled-components";
import media from "@/styles/media";

export const HeaderContainer = styled.header<{ $isMain: boolean }>`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 65px;

  color: ${({ theme }) => theme.COLOR.grey3};

  box-shadow: ${({ theme, $isMain }) => ($isMain ? "none" : theme.BOX_SHADOW.regular)};

  @media screen and (width >= 1200px) {
    padding: 0 calc(((100vw - 1200px) / 2) + 3rem);
  }

  @media screen and (width < 1200px) {
    padding: 0 2rem;
  }

  ${media.small`
    color: ${({ theme }) => theme.COLOR.grey1};
    box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
  `}
`;

// 서비스 로고
export const HeaderLogo = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  background: transparent;

  span {
    font-family: "Moirai One", system-ui;
    font-size: 3rem;
    font-weight: 900;
    color: ${({ theme }) => theme.COLOR.grey4};
  }
`;

// 네비게이션
export const HeaderNavBarContainer = styled.ul`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const HeaderItem = styled.li<{ $isMain: boolean }>`
  cursor: pointer;

  width: fit-content;
  padding: 0 0.4rem;

  font: ${({ theme }) => theme.TEXT.large};
  font-family: "Do Hyeon", sans-serif;
  color: ${({ theme }) => theme.COLOR.grey3};

  transition: 0.3s;

  &:hover,
  &.selected {
    font: ${({ theme }) => theme.TEXT.large};
    font-family: "Do Hyeon", sans-serif;
    color: ${({ theme }) => theme.COLOR.black};
    border-bottom: 3px solid ${({ theme }) => theme.COLOR.black};
  }
`;
