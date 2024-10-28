import styled from "styled-components";
import { EllipsisText, VisuallyHidden } from "@/styles/common";
import media from "@/styles/media";

export const RoomCardModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

export const HeaderContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: space-between;
`;

// 분류 배지
export const ClassificationBadgeWrapper = styled.div`
  position: absolute;

  overflow: hidden;

  width: 60px;
  height: 25px;

  border-radius: 0 0 8px;
`;

// 썸네일
export const RoomInfoThumbnail = styled.img`
  object-fit: scale-down;

  ${media.small`
    width: 100%;
    height: 120px;
  `}

  ${media.medium`
    width: calc(50% - 2rem);
    height: 140px;
  `}

  ${media.large`
    width: calc(50% - 2rem);
    height: 160px;
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
    width: calc(50% - 2rem);
    gap: 2rem;
  `} 
  
  ${media.large`
    width: calc(50% - 2rem);
    gap: 2rem;
  `};
`;

export const ManagerContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
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
    max-width: 168px;
    font: ${({ theme }) => theme.TEXT.small};

    ${EllipsisText}
  }
`;

export const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 24px;
  height: 24px;

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 50%;
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

export const RepositoryLink = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.semiSmall_bold};
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
`;

export const InfoRow = styled.div`
  display: flex;
  align-items: center;

  ${media.small`
    justify-content: space-between;
  `}
`;

export const InfoTitle = styled.span`
  width: 200px;
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey3};

  ${media.small`
    width: auto;
  `}
`;

export const InfoContent = styled.span`
  font: ${({ theme }) => theme.TEXT.small};

  span#sub {
    padding-left: 1rem;
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

// 키워드, 설명
export const DescriptionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
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
  line-height: 2rem;
  white-space: pre-line;
`;

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

// 버튼
export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;

  width: 100%;

  h2 {
    font: ${({ theme }) => theme.TEXT.small_bold};
  }
`;

export const FormContainer = styled.div`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  width: 270px;
  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey2};
  border-radius: 10px;
`;

export const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;
`;

export const MatchingSizeContainer = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;

  p {
    font: ${({ theme }) => theme.TEXT.semiSmall};
  }

  button {
    padding: 0.2rem 0.6rem;
  }
`;

export const ButtonWRapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const ScreenReader = styled.div`
  ${VisuallyHidden}
`;

export const ReviewCount = styled.span`
  font-size: ${({ theme }) => theme.TEXT.semiSmall};
`;
