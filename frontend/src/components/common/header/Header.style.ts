import styled from "styled-components";

export const HeaderContainer = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;

  box-sizing: border-box;
  width: 100%;
  height: 65px;

  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  @media screen and (width >= 1200px) {
    padding: 0 calc(((100vw - 1200px) / 2) + 3rem);
  }

  @media screen and (width < 1200px) {
    padding: 0 1rem;
  }
`;

// 서비스 로고
export const HeaderLogo = styled.button`
  font-family: "Moirai One", system-ui;
  font-size: 2rem;
  font-weight: 900;
  color: ${({ theme }) => theme.COLOR.primary2};

  background: transparent;
`;

// 네비게이션
export const HeaderNavBarContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const HeaderItem = styled.li`
  cursor: pointer;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey1};

  &:hover,
  &.selected {
    width: fit-content;
    font-weight: 700;
    color: ${({ theme }) => theme.COLOR.black};
    border-bottom: 3px solid ${({ theme }) => theme.COLOR.black};
  }
`;
