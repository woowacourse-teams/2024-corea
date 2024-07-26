import styled from "styled-components";

export const MyRevieweeContainer = styled.div`
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 1rem;
`;

export const MyRevieweeWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.5rem 2rem;

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
  }
`;

export const MyRevieweeTitle = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 200px;
`;

export const MyRevieweeContent = styled.span`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  align-items: center;

  width: 200px;
`;

export const PRLink = styled.a`
  cursor: pointer;
  text-decoration: underline;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary2};
    text-decoration: underline;
  }
`;
