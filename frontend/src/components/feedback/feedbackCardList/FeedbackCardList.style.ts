import styled, { css, keyframes } from "styled-components";

const fadeIn = keyframes`
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
    transform: translateY(0);
  }
  100% {
    opacity: 0;
    transform: translateY(20px);
  }
`;

export const FeedbackCardContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-top: 1rem;
`;

export const FeedbackMissionWrapper = styled.button`
  padding: 1rem;
  background: ${({ theme }) => theme.COLOR.primary1};
  border-radius: 10px;
`;

export const FeedbackInfoWrapper = styled.div<{ isVisible: boolean }>`
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  transition:
    opacity 0.5s ease,
    transform 0.5s ease;

  ${({ isVisible }) =>
    isVisible
      ? css`
          animation: ${fadeIn} 0.5s ease backwards;
        `
      : css`
          animation: ${fadeOut} 0.5s ease backwards;
        `}
`;

export const FeedbackMissionTitle = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const FeedbackMissionInfo = styled.div`
  display: flex;
  align-items: center;
  margin-right: 1rem;
  font: ${({ theme }) => theme.TEXT.large};
`;

export const FeedbackKeywordContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

export const FeedbackKeywordWrapper = styled.div`
  display: flex;
  align-items: center;

  padding: 0.7rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey3};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 10px;
`;
