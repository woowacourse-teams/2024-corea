import styled from "styled-components";

export const FeedbackHeader = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 1rem;
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
