import styled from "styled-components";

export const HeaderContainer = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100vw;
  height: 6.5rem;
  padding: 0 calc((100vw - 1200px) / 2);

  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  div {
    font: ${({ theme }) => theme.TEXT.large};
  }

  li {
    cursor: pointer;
    font: ${({ theme }) => theme.TEXT.semiSmall};
  }

  ul {
    display: flex;
    gap: 1rem;
  }
`;
