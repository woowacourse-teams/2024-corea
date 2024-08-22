import styled from "styled-components";
import media from "@/styles/media";

export const MyRevieweeContainer = styled.div`
  width: 100%;

  font: ${({ theme }) => theme.TEXT.small};
  font-family: "Do Hyeon", sans-serif;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
`;

export const MyRevieweeWrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  align-items: center;
  padding: 0.7rem 1rem;

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
  }
`;

export const MyRevieweeTitle = styled.span`
  font-weight: bold;
  text-align: center;
`;

export const MyRevieweeContent = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`;

export const PRLink = styled.a`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  text-decoration: underline;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary2};
    text-decoration: underline;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
`;

export const IconWrapper = styled.span`
  ${media.small`
    display: none;
`}
`;

export const ErrorWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 200px;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.secondary};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
`;
