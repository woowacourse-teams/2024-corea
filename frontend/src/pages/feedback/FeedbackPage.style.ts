import styled from "styled-components";

export const FeedbackCardContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

export const FeedbackMissionInfo = styled.div`
  margin-top: 1.5rem;
  font: ${({ theme }) => theme.TEXT.xLarge};
`;
