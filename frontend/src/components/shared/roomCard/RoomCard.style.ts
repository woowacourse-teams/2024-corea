import styled from "styled-components";
import media from "@/styles/media";

export const RoomCardContainer = styled.div`
  cursor: pointer;

  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;

  width: 100%;
  margin: 0 auto;

  font: ${({ theme }) => theme.TEXT.xSmall};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};

  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.05);
  }

  &:active {
    position: relative;
    top: 3px;
    box-shadow: ${({ theme }) => theme.BOX_SHADOW.light};
  }
`;

export const ClassificationBadgeWrapper = styled.div`
  position: absolute;
  width: 70px;
  height: 30px;
`;

export const RoomInfoThumbnail = styled.img`
  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;
  margin: 0 auto;

  object-fit: scale-down;

  ${media.small`
    height: 120px;
  `}

  ${media.medium`
    height: 140px;
  `} 
  
  ${media.large`
    height: 160px;
  `};
`;

export const RoomInformation = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  padding: 1rem;
`;

export const RoomTitle = styled.h2`
  overflow: hidden;

  width: 100%;
  padding: 1rem 0;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  text-overflow: ellipsis;
  white-space: nowrap;

  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 2px;
  height: 33px;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  align-items: flex-start;
`;

export const KeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

export const EtcContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const JoinMember = styled.span`
  display: flex;
  flex-direction: row;
  gap: 0.2rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const DeadLineText = styled.span`
  display: flex;
  gap: 0.6rem;
  align-items: center;

  padding-bottom: 0.4rem;

  font: ${({ theme }) => theme.TEXT.small};
`;

export const StyledDday = styled.span`
  display: flex;
  align-items: center;
  font: ${({ theme }) => theme.TEXT.semiSmall_bold};
  color: ${({ theme }) => theme.COLOR.error};
`;

export const LabelWrapper = styled.div`
  display: flex;
  gap: 0.5rem;
`;
