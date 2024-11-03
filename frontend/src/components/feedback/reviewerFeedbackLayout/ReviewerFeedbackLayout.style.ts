import styled from "styled-components";

interface ModalQuestionProps {
  required?: boolean;
}

export const FeedbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;

  width: 100%;
  min-width: 375px;
  max-width: 700px;
  margin: auto;
`;

export const FeedbackContainerHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;

export const PageType = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const PageTitle = styled.p`
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

export const PageQuestion = styled.p<ModalQuestionProps>`
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
