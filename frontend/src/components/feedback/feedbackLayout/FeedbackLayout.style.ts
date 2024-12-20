import styled from "styled-components";
import { EllipsisText } from "@/styles/common";

export const FeedbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
  min-width: 335px;
  max-width: 700px;
  margin: auto;
`;

export const FeedbackContainerHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

export const PageType = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const RoomInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.light};
`;

export const RoomInfoWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const PageTitle = styled.p`
  width: calc(100% - 120px);
  font: ${({ theme }) => theme.TEXT.large_bold};
  color: ${({ theme }) => theme.COLOR.black};
  ${EllipsisText}
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
`;

export const Keywords = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const ItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  margin-top: 1rem;
`;

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;
