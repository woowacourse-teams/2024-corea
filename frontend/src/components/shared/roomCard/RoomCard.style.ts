import styled from "styled-components";
import media from "@/styles/media";

export const RoomCardContainer = styled.div`
  cursor: pointer;

  overflow: hidden;
  display: flex;
  flex-direction: column;

  width: 100%;
  margin: 0 auto;

  font: ${({ theme }) => theme.TEXT.xSmall};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.05);
  }

  &:active {
    position: relative;
    top: 3px;
    box-shadow: 0 1px 1px rgb(0 0 0 / 10%);
  }
`;

export const RoomInfoThumbnail = styled.img`
  display: flex;
  align-items: center;

  width: 90%;
  height: 90%;
  margin: 0 auto;
  padding-top: 0.5rem;

  ${media.small`
    height: 100px;
  `}

  ${media.medium`
    height: 120px;
  `} 
  
  ${media.large`
    height: 130px;
  `};

  object-fit: scale-down;
`;

export const RoomInformation = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  padding: 0.5rem;

  ${media.small`
    gap: 0.1rem;
    letter-spacing: -1.3px;
  `}
`;

export const RoomTitle = styled.h2`
  overflow: hidden;

  padding-top: 1rem;

  font: ${({ theme }) => theme.TEXT.medium};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 2px;
  margin-bottom: 0.6rem;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
`;

export const KeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

export const MoreKeywords = styled.span`
  font: ${({ theme }) => theme.TEXT.medium};
  color: ${({ theme }) => theme.COLOR.grey4};
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
`;

export const DeadLineText = styled.span`
  display: flex;
  gap: 0.5rem;
  margin: 0 0 0.5rem;
  font: ${({ theme }) => theme.TEXT.small};
`;

export const StyledDday = styled.span`
  color: ${({ theme }) => theme.COLOR.error};
`;
