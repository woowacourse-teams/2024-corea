import styled from "styled-components";

export const FeedbackFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8rem;
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

export const ModalQuestion = styled.legend<{ $isInvalid?: boolean }>`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme, $isInvalid }) => ($isInvalid ? theme.COLOR.error : theme.COLOR.grey4)};
`;

export const Required = styled.span`
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.error};
`;

export const TextareaWrapper = styled.div`
  margin-top: 1.2rem;
`;
