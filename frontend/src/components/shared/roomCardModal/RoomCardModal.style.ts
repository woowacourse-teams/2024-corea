import styled from "styled-components";
import media from "@/styles/media";

export const RoomCardModalContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem 2%;
  justify-content: space-between;
`;

// 썸네일
export const RoomInfoThumbnail = styled.img`
  object-fit: scale-down;

  ${media.small`
    width: 100%;
    height: 100px;
  `}

  ${media.medium`
    width: 49%;
    height: 120px;
  `}

  ${media.large`
    width: 49%;
    height: 130px;
  `}
`;

// 프로필, 매니저, 모집여부
export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;

  ${media.small`
    width: 100%;
    height: 50px;
    gap: 1rem;
  `}

  ${media.medium`
    width: 49%;
    height: 120px;
    gap: 2rem;
  `}

  ${media.large`
    width: 49%;
    height: 130px;
    gap: 2rem;
  `}
`;

export const ManagerContainer = styled.div`
  display: flex;
  gap: 0.5rem;
  justify-content: flex-start;
  font: ${({ theme }) => theme.TEXT.small};
`;

export const ProfileContainer = styled.div`
  display: flex;
  gap: 2px;
  align-items: center;

  img {
    width: 20px;
    height: 20px;
  }
`;

// 방 이름, 저장소 링크
export const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  ${media.small`
    flex-direction: row;
  `}
`;

export const RoomTitle = styled.h2`
  overflow: hidden;
  font: ${({ theme }) => theme.TEXT.medium};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const RepositoryLink = styled.a`
  cursor: pointer;

  display: flex;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.primary2};
  text-decoration: underline;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary3};
    text-decoration: underline;
  }
`;

// 마감 날짜, 모집 인원
export const EtcContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  width: 100%;
  margin: 0 auto;
  padding: 1rem;

  background-color: ${({ theme }) => theme.COLOR.white};
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: 0 2px 4px rgb(0 0 0 / 10%);
`;

export const InfoRow = styled.div`
  display: flex;
  align-items: center;
`;

export const InfoTitle = styled.span`
  width: 30%;
  font: ${({ theme }) => theme.TEXT.xSmall};
`;

export const InfoContent = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;

// 키워드, 설명
export const DescriptionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

export const KeywordsContainer = styled.div`
  display: flex;
  gap: 2px;
`;

export const ContentContainer = styled.p`
  font: ${({ theme }) => theme.TEXT.xSmall};
`;
