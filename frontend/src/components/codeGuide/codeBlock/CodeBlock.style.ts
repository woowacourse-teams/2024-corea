import styled from "styled-components";

export const StyledPre = styled.pre`
  overflow-x: auto;

  padding: 1rem;

  line-height: 1.2rem;

  background-color: ${({ theme }) => theme.COLOR.grey4};
  border-radius: 8px;
`;

export const StyledCode = styled.code`
  font: ${({ theme }) => theme.TEXT.xSmall};
  line-height: 2rem;
  color: ${({ theme }) => theme.COLOR.white};
`;
