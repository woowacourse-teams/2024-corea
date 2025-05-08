import styled from "styled-components";
import { HeaderHeight } from "@/styles/layout";
import media from "@/styles/media";
import { Z_INDEX } from "@/styles/zIndex";

export const HeaderContainer = styled.header<{ $showShadow: boolean; $shouldFixed: boolean }>`
  position: ${({ $shouldFixed }) => ($shouldFixed ? "fixed" : "relative")};
  z-index: ${Z_INDEX.header};

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: ${HeaderHeight};

  color: ${({ theme }) => theme.COLOR.grey3};

  box-shadow: ${({ theme, $showShadow }) => ($showShadow ? theme.BOX_SHADOW.regular : "none")};

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
