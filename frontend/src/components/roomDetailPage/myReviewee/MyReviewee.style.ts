import styled from "styled-components";
import media from "@/styles/media";

export const MyRevieweeContainer = styled.div`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
`;

export const MyRevieweeWrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  place-items: center center;
  padding: 0.7rem 1rem;

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
  }
`;

export const MyRevieweeTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
  text-align: center;
`;

export const MyRevieweeContent = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: center;
`;

export const PRLink = styled.a`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.semiSmall};
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

export const GuidanceWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 200px;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;

  p.process-waiting {
    font: ${({ theme }) => theme.TEXT.small_bold};
    color: ${({ theme }) => theme.COLOR.secondary};
  }
`;
