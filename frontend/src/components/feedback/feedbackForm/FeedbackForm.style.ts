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
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small};
  font-weight: 600;
  color: ${({ theme }) => theme.COLOR.black};

  ${({ required, theme }) =>
    required &&
    `
    &::after {
      content: "*필수입력";
      font: ${theme.TEXT.semiSmall};
      font-weight: 400;
      color: ${theme.COLOR.error};
      margin-left: 4px;
    }
  `}
`;

export const StyledTextarea = styled.p`
  display: flex;
  width: 100%;
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
