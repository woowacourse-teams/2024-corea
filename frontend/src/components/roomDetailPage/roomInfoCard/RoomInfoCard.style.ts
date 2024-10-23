import styled from "styled-components";
import { EllipsisText } from "@/styles/common";
import media from "@/styles/media";

export const RoomInfoCardContainer = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;

  width: 100%;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const ClassificationBadgeWrapper = styled.div`
  position: absolute;

  overflow: hidden;

  width: 70px;
  height: 30px;

  border-radius: 0 0 8px;

  ${media.small`
    right: 0;
    border-radius: 0 0 0 8px;
  `}
`;

export const RoomInfoCardImg = styled.img`
  overflow: hidden;
  align-self: center;

  width: 30rem;
  height: 100%;
  margin: 1rem;

  object-fit: scale-down;

  ${media.small`
    display: none;
  `}
`;

export const RoomInfoCardContent = styled.div`
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 100%;
  padding: 2rem 1rem;
`;

export const RoomHeaderWrapper = styled.div`
  display: flex;
  flex-flow: row wrap;
  align-items: flex-start;
  justify-content: space-between;

  margin-bottom: 1rem;
  padding-bottom: 1rem;

  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};

  ${media.small`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  `}
`;

export const RoomTitle = styled.span`
  max-width: calc(100% - 160px);
  font: ${({ theme }) => theme.TEXT.large_bold};
  line-height: 2.5rem;
  color: ${({ theme }) => theme.COLOR.black};
`;

export const RepositoryLink = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  padding-right: 1rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const RoomContentBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;

  &:last-child {
    margin-bottom: 0;
  }
`;

export const RoomTagBox = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
`;

export const NoKeywordText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey2};
`;

export const RoomContentSmall = styled.span`
  display: flex;
  gap: 1rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small_bold};
  line-height: 2rem;
  color: ${({ theme }) => theme.COLOR.black};

  span {
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.grey4};
  }

  span#githubid {
    max-width: 210px;
    font: ${({ theme }) => theme.TEXT.small_bold};

    ${EllipsisText}
  }

  div {
    display: flex;
    flex-direction: row;
    gap: 1rem;
    align-items: center;
  }

  ${media.small`
    gap: 0.4rem;
  `}
`;

export const ContentLineBreak = styled.div`
  display: flex;
  flex-flow: row wrap;
`;

export const DateTimeText = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const StyledDday = styled.p`
  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.error};
`;

export const RoomKeyword = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0 1rem;

  background-color: ${({ theme }) => theme.COLOR.primary1};
  border: none;
  border-radius: 5px;
`;
