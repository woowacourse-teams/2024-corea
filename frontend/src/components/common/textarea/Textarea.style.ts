import styled from "styled-components";

export const Textarea = styled.textarea<{ $error: boolean }>`
  resize: none;

  overflow: hidden auto;

  width: 100%;
  padding: 0.6rem 1.1rem;

  font-size: 1rem;

  border: 1px solid
    ${(props) => (props.$error ? props.theme.COLOR.secondary : props.theme.COLOR.grey1)};
  border-radius: 6px;

  &::-webkit-scrollbar {
    width: 5px;
  }

  &::-webkit-scrollbar-thumb {
    height: 30%;
    background: rgb(92 92 92 / 40%);
    border-radius: 6px;
  }

  &::-webkit-scrollbar-track {
    background: rgb(92 92 92 / 10%);
  }
`;
