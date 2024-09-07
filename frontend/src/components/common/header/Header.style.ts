import styled from "styled-components";
import media from "@/styles/media";

export const HeaderContainer = styled.header<{ $isMain: boolean }>`
  position: relative;
  z-index: 999;

  display: flex;
  align-items: center;
  justify-content: space-between;

  box-sizing: border-box;
  width: 100%;
  height: 65px;

  color: ${({ theme }) => theme.COLOR.grey3};

  box-shadow: ${({ $isMain }) => ($isMain ? "none" : "0 4px 4px rgb(0 0 0 / 10%)")};

  @media screen and (width >= 1200px) {
    padding: 0 calc(((100vw - 1200px) / 2) + 3rem);
  }

  @media screen and (width < 1200px) {
    padding: 0 2rem;
  }

  ${media.small`
    color: ${({ theme }) => theme.COLOR.grey1};
    box-shadow: 0 4px 4px rgb(0 0 0 / 10%);
  `}
`;

// 서비스 로고
export const HeaderLogo = styled.button<{ $isMain: boolean }>`
  font-family: "Moirai One", system-ui;
  font-size: 3rem;
  font-weight: 900;
  color: ${({ theme, $isMain }) => ($isMain ? theme.COLOR.white : theme.COLOR.grey3)};

  background: transparent;

  ${media.small`
    color: ${({ theme }) => theme.COLOR.black};
  `}
`;

// 네비게이션
export const HeaderNavBarContainer = styled.div`
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
  color: ${({ theme, $isMain }) => ($isMain ? theme.COLOR.white : theme.COLOR.grey3)};

  ${media.small`
    color: ${({ theme }) => theme.COLOR.black};
  `}
  transition: 0.3s;

  &:hover,
  &.selected {
    font: ${({ theme }) => theme.TEXT.large};
    font-family: "Do Hyeon", sans-serif;
    color: ${({ theme }) => theme.COLOR.black};
    border-bottom: 3px solid ${({ theme }) => theme.COLOR.black};
  }
`;
