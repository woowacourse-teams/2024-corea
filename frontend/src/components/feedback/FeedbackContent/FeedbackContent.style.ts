import styled from "styled-components";

export const FeedbackContentContainer = styled.div<{ $isWrited: boolean }>`
  overflow: hidden auto;
  display: flex;
  flex-direction: column;
  gap: 5rem;

  width: 100%;
  height: 100%;

  filter: ${(props) => (props.$isWrited ? "none" : "blur(7px)")};

  -ms-overflow-style: none;

  &::-webkit-scrollbar {
    display: none;
  }
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
`;

export const FeedbackSubTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.small_bold};
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
