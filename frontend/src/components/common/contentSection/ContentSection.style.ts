import styled from "styled-components";

export const ContentSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

export const ContentSectionHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const ContentSectionTitle = styled.h2`
  font: ${({ theme }) => theme.TEXT.xLarge_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;
