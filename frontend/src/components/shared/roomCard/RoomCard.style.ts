import styled from "styled-components";

export const RoomCardContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;

  width: 200px;
  height: 240px;

  font: ${({ theme }) => theme.TEXT.xSmall};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  &:hover {
    transform: scale(1.05);
    transition: 0.3s;
    transition-property: transform;
  }

  &:active {
    position: relative;
    top: 3px;
    box-shadow: 0 1px 1px rgb(0 0 0 / 10%);
  }
`;

export const RoomInfoThumbnail = styled.img`
  width: 100%;
  height: 130px;
  object-fit: scale-down;
`;

export const RoomInformation = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;

  height: calc(100% - 120px);
  padding: 0.5rem;
`;

export const RoomTitle = styled.h2`
  overflow: hidden;
  font: ${({ theme }) => theme.TEXT.small};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 2px;
  margin-bottom: 0.6rem;
`;

export const MoreKeywords = styled.span`
  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const EtcContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;
