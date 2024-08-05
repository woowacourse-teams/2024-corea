import styled from "styled-components";
import media from "@/styles/media";

export const FeedbackCardContainer = styled.div`
  overflow-y: hidden;
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 40%;
  min-width: 420px;
  height: 600px;
  padding: 1rem;

  border-radius: 10px;
  box-shadow:
    rgb(17 17 26 / 10%) 0 4px 16px,
    rgb(17 17 26 / 5%) 0 8px 32px;

  ${media.small`
    width: 100%;
  `}
`;

export const FeedbackScoreContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const FeedbackKeywordContainer = styled.div`
  height: 130px;
`;

export const FeedbackKeywordWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-top: 1rem;
`;

export const FeedbackTitle = styled.h3`
  font-weight: bold;
`;

export const FeedbackKeyword = styled.div`
  padding: 1rem;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 5px;
`;

export const FeedbackDetailContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1rem;

  height: 200px;
`;

export const FeedbackDetail = styled.p`
  overflow: hidden;

  height: 120px;

  line-height: 1.2rem;
  text-overflow: ellipsis;
  white-space: break-spaces;
`;
