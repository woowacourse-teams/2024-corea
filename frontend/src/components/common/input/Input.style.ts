import styled from "styled-components";

export const InputWrapper = styled.div`
  position: relative;
  width: 100%;
`;

export const StyledInput = styled.input<{ $error: boolean }>`
  width: 100%;
  padding: 0.6rem 1.1rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};

  border: 1px solid ${(props) => (props.$error ? props.theme.COLOR.error : props.theme.COLOR.grey1)};
  border-radius: 6px;
  outline-color: ${({ theme }) => theme.COLOR.black};
`;

export const CharCount = styled.div`
  position: absolute;
  right: 10px;
  bottom: -20px;

  font-size: 12px;
  color: #888;
`;
