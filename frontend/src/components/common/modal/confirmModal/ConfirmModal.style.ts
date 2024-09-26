import styled from "styled-components";

export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: space-evenly;

  .cancel-button {
    color: ${({ theme }) => theme.COLOR.primary2};
    background-color: ${({ theme }) => theme.COLOR.white};
    border: 2px solid ${({ theme }) => theme.COLOR.primary2};
  }
`;
