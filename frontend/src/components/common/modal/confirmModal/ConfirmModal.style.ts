import styled from "styled-components";

export const ConfirmModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5rem;

  padding: 3rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: space-evenly;

  .cancel-button {
    color: ${({ theme }) => theme.COLOR.primary2};
    background-color: ${({ theme }) => theme.COLOR.white};
    border: 2px solid ${({ theme }) => theme.COLOR.primary2};
  }
`;
