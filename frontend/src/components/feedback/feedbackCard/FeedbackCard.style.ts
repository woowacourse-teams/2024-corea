import styled from "styled-components";
import { VisuallyHidden } from "@/styles/common";
import media from "@/styles/media";

export const ScreenReader = styled.div`
  ${VisuallyHidden}
`;

export const FeedbackCardContainer = styled.div`
  position: relative;
  overflow: hidden;
  width: 40%;
  height: 700px;

  ${media.medium`
    width: 100%;
    max-width: 420px;
  `}

  ${media.small`
    width: 100%;
  `}
`;

export const FeedbackCardBox = styled.div<{ $isTypeDevelop: boolean }>`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  height: 100%;

  border: 3px solid
    ${({ theme, $isTypeDevelop }) =>
      $isTypeDevelop ? theme.COLOR.primary2 : theme.COLOR.secondary};
  border-radius: 10px;
`;

export const FeedbackBody = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 100%;
  padding: 2rem;
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
