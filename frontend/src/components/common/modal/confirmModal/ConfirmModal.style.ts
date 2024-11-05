import styled from "styled-components";

export const ConfirmModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5rem;

  padding: 3rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  line-height: normal;
  text-align: center;
  white-space: pre-line;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: space-evenly;
`;
