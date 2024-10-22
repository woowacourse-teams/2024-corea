import styled from "styled-components";

interface ModalQuestionProps {
  required?: boolean;
}

export const FeedbackFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

export const ItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const ModalQuestion = styled.p<ModalQuestionProps>`
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey4};

  span {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.error};
  }
`;
