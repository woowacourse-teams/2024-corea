import styled from "styled-components";

export const ContentSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ContentSectionHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const TitleContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: flex-end;
  justify-content: center;
`;

export const ContentSectionTitle = styled.h2`
  font: ${({ theme }) => theme.TEXT.xLarge_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const ContentSectionSubtitle = styled.h3`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;
