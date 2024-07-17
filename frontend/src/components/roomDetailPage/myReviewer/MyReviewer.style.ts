import styled from "styled-components";

export const MyReviewerContainer = styled.div`
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 1rem;
`;

export const MyReviewerTitle = styled.span`
  width: 10rem;
  padding: 1rem 2rem;
`;

export const MyReviewerContent = styled.span`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;

  width: 10rem;
  padding: 1rem 2rem;
`;

export const MyReviewerWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
  }
`;

export const PRLink = styled.a`
  cursor: pointer;
  text-decoration: underline !important;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary2};
    text-decoration: underline !important;
  }
`;
