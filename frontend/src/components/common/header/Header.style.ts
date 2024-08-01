import styled from "styled-components";

export const HeaderContainer = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100vw;
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
  font: ${({ theme }) => theme.TEXT.large};
  background: transparent;
`;

// 네비게이션
export const HeaderNavBarContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const HeaderList = styled.ul`
  display: flex;
  gap: 1rem;
`;

export const HeaderItem = styled.li`
  cursor: pointer;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey1};

  &.selected {
    width: fit-content;
    font-weight: 700;
    color: ${({ theme }) => theme.COLOR.black};
    border-bottom: 3px solid ${({ theme }) => theme.COLOR.black};
  }
`;
