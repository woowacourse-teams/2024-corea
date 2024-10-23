import styled from "styled-components";

interface ModalQuestionProps {
  required?: boolean;
}

export const FeedbackFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

export const ItemContainer = styled.fieldset`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const QuestionContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
  align-items: center;
`;

export const ModalQuestion = styled.legend<ModalQuestionProps>`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const Required = styled.span`
  padding-top: 0.5rem;
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.error};
`;

export const TextareaWrapper = styled.div`
  margin-top: 1.2rem;
`;
