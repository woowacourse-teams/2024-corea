import styled, { css, keyframes } from "styled-components";
import media from "@/styles/media";

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

const fadeInOut = keyframes`
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
`;

export const EmptyContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;

  margin-top: 4rem;

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;

export const Character = styled.img`
  width: 70%;
  max-width: 270px;
`;

export const FeedbackCardContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-top: 1rem;

  ${media.small`
    gap: 2rem;
  `}
`;

export const FeedbackMissionWrapper = styled.button<{ $isSelected: boolean }>`
  display: flex;
  flex-flow: row wrap;
  align-items: center;
  justify-content: space-between;

  padding: 1.6rem;
  gap: 1rem;

  background: ${({ theme, $isSelected }) =>
    $isSelected ? theme.COLOR.primary1 : theme.COLOR.grey0};
  border-radius: 10px;
`;

export const FeedbackInfoWrapper = styled.div<{ $isVisible: boolean }>`
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  transition:
    opacity 0.5s ease,
    transform 0.5s ease;

  ${({ $isVisible }) =>
    $isVisible
      ? css`
          animation: ${fadeIn} 0.5s ease backwards;
        `
      : css`
          animation: ${fadeOut} 0.5s ease backwards;
        `}
`;

export const FeedbackMissionTitle = styled.div`
  display: flex;
  flex-flow: row wrap;
  align-items: center;
`;

export const FeedbackMissionPrompt = styled.span<{ $isSelected: boolean }>`
  display: ${({ $isSelected }) => ($isSelected ? "none" : "inline")};
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.grey3};
  animation: ${fadeInOut} 2s infinite;
`;

export const FeedbackMissionInfo = styled.div`
  display: flex;
  align-items: center;
  margin-right: 1rem;
  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const FeedbackKeywordContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;
