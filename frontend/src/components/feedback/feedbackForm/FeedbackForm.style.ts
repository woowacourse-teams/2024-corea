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

export const ModalQuestion = styled.legend<ModalQuestionProps>`
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const Required = styled.span`
  padding-top: 0.5rem;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.error};
`;
