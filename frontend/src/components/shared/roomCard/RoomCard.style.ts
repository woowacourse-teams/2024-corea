import styled from "styled-components";
import media from "@/styles/media";

export const RoomCardContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;

  margin: 0 auto;

  font: ${({ theme }) => theme.TEXT.xSmall};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  cursor: pointer;

  ${media.small`
    width: 100%;
    height: auto;
  `}

  ${media.medium`
    width: 180px;
    height: 220px;
  `}

  ${media.large`
    width: 200px;
    height: 240px;
  `}

  &:hover {
    ${media.medium`
      transform: scale(1.05);
    `}
    ${media.large`
      transform: scale(1.05);
    `}
  }

  &:active {
    position: relative;
    top: 3px;
    box-shadow: 0 1px 1px rgb(0 0 0 / 10%);
  }
`;

export const RoomInfoThumbnail = styled.img`
  width: 100%;
  object-fit: scale-down;

  ${media.small`
    height: 100px;
  `}

  ${media.medium`
    height: 120px;
  `}

  ${media.large`
    height: 130px;
  `}
`;

export const RoomInformation = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  ${media.small`
    gap: 0.1rem;
    letter-spacing: -1.3px;
  `}
  padding: 0.5rem;
`;

export const RoomTitle = styled.h2`
  overflow: hidden;
  font: ${({ theme }) => theme.TEXT.small};
  ${media.small`
    font: ${({ theme }) => theme.TEXT.semiSmall};
  `}
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 2px;
  margin-bottom: 0;

  ${media.large`
    margin-bottom: 0.6rem;
  `}
`;

export const MoreKeywords = styled.span`
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const EtcContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;
