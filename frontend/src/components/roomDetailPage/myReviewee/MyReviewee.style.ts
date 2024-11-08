import styled from "styled-components";
import { EllipsisText } from "@/styles/common";
import media from "@/styles/media";

export const MyRevieweeTable = styled.table`
  border-collapse: separate;

  width: 100%;

  font: ${({ theme }) => theme.TEXT.small};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
`;

export const MyRevieweeTableHead = styled.thead``;

export const MyRevieweeTableBody = styled.tbody`
  tr:last-child {
    border-bottom: none;
  }
`;

export const MyRevieweeTableRow = styled.tr`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  place-items: center center;

  box-sizing: content-box;
  height: 40px;
  padding: 0.7rem 1rem;

  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
`;

export const MyRevieweeTableHeader = styled.th`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
  text-align: center;
  word-break: keep-all;
`;

export const MyRevieweeContent = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 40px;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: center;
  word-break: keep-all;

  p {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    text-align: center;
  }
`;

export const MyRevieweeId = styled.span`
  display: block;

  box-sizing: border-box;
  width: 100%;
  max-width: 100px;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: left;

  ${EllipsisText}

  ${media.medium`
    max-width: 120px;
  `}

  ${media.large`
    max-width: 100%;
  `}
`;

export const PRLink = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;

  font: ${({ theme }) => theme.TEXT.semiSmall};
`;

export const IconWrapper = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;

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

  p.process-paused {
    font: ${({ theme }) => theme.TEXT.small_bold};
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

export const ExtraInformation = styled.span`
  display: flex;
  justify-content: flex-end;
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;
