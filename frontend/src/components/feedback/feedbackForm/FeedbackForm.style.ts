import styled from "styled-components";

interface ModalQuestionProps {
  required?: boolean;
}

export const ItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const ModalQuestion = styled.p<ModalQuestionProps>`
  display: flex;
  flex-direction: row;

  font: ${({ theme }) => theme.TEXT.small};
  font-weight: 600;
  color: ${({ theme }) => theme.COLOR.black};

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
