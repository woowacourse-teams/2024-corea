import styled from "styled-components";
import { EllipsisText, VisuallyHidden } from "@/styles/common";
import media from "@/styles/media";

export const FeedbackCardContainer = styled.div<{ $isTypeDevelop: boolean }>`
  position: relative;

  overflow-y: hidden;
  display: flex;
  flex-direction: column;
  gap: 3rem;

  width: 40%;
  height: 700px;
  padding: 1rem;

  border: 3px solid
    ${({ theme, $isTypeDevelop }) =>
      $isTypeDevelop ? theme.COLOR.primary2 : theme.COLOR.secondary};
  border-radius: 10px;

  ${media.medium`
    width: 100%;
    max-width: 420px;
  `}

  ${media.small`
    width: 100%;
  `}
`;

export const Overlay = styled.div`
  pointer-events: auto;

  position: absolute;
  top: 60px;
  left: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: calc(100% - 60px);

  background-color: rgb(255 255 255 / 50%);
`;

export const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 100%;

  font: ${({ theme }) => theme.TEXT.large_bold};
`;

export const FeedbackContent = styled.div<{ $isWrited: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 3rem;
  filter: ${(props) => (props.$isWrited ? "none" : "blur(7px)")};
`;

export const FeedbackScoreContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const FeedbackKeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  height: fit-content;
`;

export const FeedbackKeywordWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-content: flex-start;

  height: 122px;
`;

export const FeedbackHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const FeedbackProfile = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  align-items: center;
`;

export const FeedbackType = styled.span<{ $isTypeDevelop: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme, $isTypeDevelop }) =>
    $isTypeDevelop ? theme.COLOR.primary2 : theme.COLOR.secondary};
  text-align: right;
  white-space: pre-line;

  p {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

export const FeedbackTitle = styled.h3`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const FeedbackSubTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.small};
`;

export const FeedbackKeyword = styled.div`
  height: fit-content;
  padding: 1rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 5px;
`;

export const FeedbackDetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const FeedbackDetail = styled.p`
  height: 172px;
  font: ${({ theme }) => theme.TEXT.small};
  line-height: 2.2rem;

  ${EllipsisText}
`;

export const ScreenReader = styled.div`
  ${VisuallyHidden}
`;
