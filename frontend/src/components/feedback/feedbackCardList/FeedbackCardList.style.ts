import styled, { css, keyframes } from "styled-components";
import { VisuallyHidden } from "@/styles/common";
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
  max-width: 200px;
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
  gap: 1rem;
  align-items: center;
  justify-content: space-between;

  padding: 1.6rem;

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
  gap: 1rem;
  align-items: center;
`;

export const FeedbackMissionPrompt = styled.span<{ $isSelected: boolean }>`
  display: ${({ $isSelected }) => ($isSelected ? "none" : "inline")};
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.grey3};
  animation: ${fadeInOut} 2s infinite;
`;

export const FeedbackMissionInfo = styled.div``;

export const FeedbackTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const FeedbackCount = styled.span`
  margin-left: 0.5rem;
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.primary2};
`;

export const FeedbackKeywordContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-flow: row wrap;
  gap: 1rem;
`;

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

export const ScreenReader = styled.div`
  ${VisuallyHidden}
`;

export const FeedbackLink = styled.button`
  display: flex;
  align-items: center;
  color: ${({ theme }) => theme.COLOR.black};
  background-color: transparent;
`;
