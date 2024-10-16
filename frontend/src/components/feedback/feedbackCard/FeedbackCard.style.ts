import styled from "styled-components";
import media from "@/styles/media";

export const FeedbackCardContainer = styled.div<{ $isTypeDevelop: boolean }>`
  overflow-y: hidden;
  display: flex;
  flex-direction: column;
  gap: 3rem;

  width: 40%;
  height: 600px;
  padding: 1rem;

  border: 3px solid
    ${({ theme, $isTypeDevelop }) =>
      $isTypeDevelop ? theme.COLOR.primary2 : theme.COLOR.secondary};
  border-radius: 10px;

  ${media.medium`
    width: 100%;
  `}

  ${media.small`
    width: 100%;
  `}
`;

export const FeedbackScoreContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
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
  padding: 1rem;
  font: ${({ theme }) => theme.TEXT.small};
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
  overflow: hidden auto;

  height: 172px;

  font: ${({ theme }) => theme.TEXT.small};
  line-height: 2.2rem;
  text-overflow: ellipsis;
  white-space: break-spaces;
`;
