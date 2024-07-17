import styled from "styled-components";

export const ContentFormContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ContentFormTitle = styled.h2`
  padding-bottom: 1rem;
  font: ${({ theme }) => theme.TEXT.large};
`;
