import styled from "styled-components";

export const FeedbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const ModalType = styled.p`
  font: ${({ theme }) => theme.TEXT.small};
  font-weight: 600;
  color: ${({ theme }) => theme.COLOR.black};
`;

export const ModalTitle = styled.p`
  margin: -1rem 0 -1.4rem;
  font: ${({ theme }) => theme.TEXT.large};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const Keywords = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 1rem;
`;
