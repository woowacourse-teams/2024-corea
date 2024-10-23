import styled from "styled-components";
import { EllipsisText } from "@/styles/common";
import media from "@/styles/media";

export const TotalContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 12px;
`;

export const MessageWrapper = styled.span`
  padding: 2rem 0;
  font: ${({ theme }) => theme.TEXT.small};
  text-align: center;
`;

export const RenewButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`;

export const ParticipantListContainer = styled.div`
  display: grid;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 100%;

  ${media.small`
    grid-template-columns: 1fr 1fr ;
  `};
  ${media.medium`
    grid-template-columns: 1fr 1fr 1fr;
  `};
  ${media.large`
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr;
  `};
`;

export const ParticipantInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  align-items: center;
  justify-content: center;

  height: 160px;

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 6px;
`;

export const ProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  align-items: center;

  margin: 0 auto;
`;

export const ProfileNickname = styled.div`
  max-width: 80rem;
  font: ${({ theme }) => theme.TEXT.small};
  text-align: center;

  ${EllipsisText}
`;

export const PRLink = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.semiSmall};
`;

export const RefreshRemainTime = styled.span`
  box-sizing: content-box;
  width: 2rem;
  height: 2rem;
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
