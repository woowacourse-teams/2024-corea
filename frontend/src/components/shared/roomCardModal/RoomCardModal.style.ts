import styled from "styled-components";
import media from "@/styles/media";

export const RoomCardModalContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: space-between;

  :last-child {
    gap: 0;
  }
`;

// 썸네일
export const RoomInfoThumbnail = styled.img`
  object-fit: scale-down;

  ${media.small`
    width: 100%;
    height: 100px;
  `}

  ${media.medium`
    width: 47%;
    height: 120px;
  `}

  ${media.large`
    width: 47%;
    height: 130px;
  `}
`;

// 프로필, 매니저, 모집여부
export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;

  ${media.small`
    width: 100%;
    gap: 1rem;
  `}

  ${media.medium`
    width: 48%;
    gap: 2rem;
  `} 
  
  ${media.large`
    width: 48%;
    gap: 2rem;
  `};
`;

export const ManagerContainer = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  font: ${({ theme }) => theme.TEXT.small};
`;

export const ProfileContainer = styled.div`
  display: flex;
  gap: 0.4rem;
  align-items: center;

  img {
    width: 20px;
    height: 20px;
  }

  span {
    font: ${({ theme }) => theme.TEXT.small};
  }
`;

// 방 이름, 저장소 링크
export const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const RoomTitle = styled.h2`
  overflow: hidden;
  margin-bottom: 1rem;
  font: ${({ theme }) => theme.TEXT.large_bold};
`;

export const RepositoryLink = styled.a`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small_bold};
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
  width: 200px;
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const InfoContent = styled.span`
  font: ${({ theme }) => theme.TEXT.small};
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
  margin-bottom: 0.6rem;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
`;

export const KeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

export const ContentContainer = styled.p`
  font: ${({ theme }) => theme.TEXT.small};
`;

// 버튼
export const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;
