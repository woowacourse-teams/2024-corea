import styled from "styled-components";
import media from "@/styles/media";

export const FeedbackCardContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 40%;
  min-width: 420px;
  height: 500px;
  padding: 1rem;

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 10px;

  ${media.small`
    width: 100%;
  `}
`;

export const FeedbackKeywordContainer = styled.div`
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
  background: ${({ theme }) => theme.COLOR.grey1};
  border-radius: 5px;
`;

export const FeedbackDetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const FeedbackDetail = styled.p`
  line-height: 20px;
  white-space: break-spaces;
`;
