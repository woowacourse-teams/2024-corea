import styled from "styled-components";

export const FeedbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const FeedbackContainerHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;

export const ModalType = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const ModalTitle = styled.p`
  font: ${({ theme }) => theme.TEXT.large_bold};
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

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;
