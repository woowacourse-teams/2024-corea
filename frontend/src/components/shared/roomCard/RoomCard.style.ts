import styled from "styled-components";

export const RoomCardContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;

  width: 14rem;
  height: 18rem;

  border: 0.1rem solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 1.5rem;
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
  height: 10rem;
  object-fit: scale-down;
`;

export const RoomInformation = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 100%;
  height: 8rem;
  padding: 1rem;
`;

export const MainInfo = styled.div`
  display: flex;
  flex-direction: column;
`;

export const RoomTitle = styled.h2`
  overflow: hidden;
  font: ${({ theme }) => theme.TEXT.small};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const RecruitmentDeadline = styled.p`
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;

export const EtcInfo = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font: ${({ theme }) => theme.TEXT.xSmall};
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 0.2rem;
`;

export const RoomKeyword = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0 0.2rem;

  background-color: ${({ theme }) => theme.COLOR.primary1};
  border: none;
  border-radius: 5px;
`;
