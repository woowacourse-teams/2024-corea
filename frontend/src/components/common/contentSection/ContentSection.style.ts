import styled from "styled-components";

export const ContentSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ContentSectionTitle = styled.h2`
  font: ${({ theme }) => theme.TEXT.large};
`;
