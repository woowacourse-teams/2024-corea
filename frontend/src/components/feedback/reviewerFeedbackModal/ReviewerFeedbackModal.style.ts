import styled from "styled-components";

interface ModalQuestionProps {
  required?: boolean;
}

export const FeedbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const ModalType = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const ModalTitle = styled.p`
  margin: -1rem 0 -1.4rem;
  font: ${({ theme }) => theme.TEXT.large_bold};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const Keywords = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const ItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const ModalQuestion = styled.p<ModalQuestionProps>`
  font: ${({ theme }) => theme.TEXT.small};
  font-weight: 600;

  ${({ required, theme }) =>
    required &&
    `
    &::after {
      content: "*";
      color: ${theme.COLOR.error};
      margin-left: 4px;
    }
  `}
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
