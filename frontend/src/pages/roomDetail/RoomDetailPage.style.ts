import styled from "styled-components";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5rem;
`;

export const StyledDescription = styled.p`
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey2};
`;
