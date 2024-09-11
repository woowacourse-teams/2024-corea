import styled from "styled-components";
import media from "@/styles/media";

export const RoomInfoCardContainer = styled.div`
  display: flex;

  width: 100%;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);
`;

export const RoomInfoCardImg = styled.img`
  overflow: hidden;
  align-self: center;

  width: 15rem;
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
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;

  margin-bottom: 1rem;
  padding-bottom: 0.5rem;

  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};

  ${media.small`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 0.6rem;
  `}
`;

export const RoomTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.large};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const RepositoryLink = styled.a`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.primary2};
  text-decoration: underline;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary3};
    text-decoration: underline;
  }
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

export const RoomContentSmall = styled.span`
  display: flex;
  gap: 1rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};

  ${media.small`
    gap: 0.4rem;
  `}
`;

export const StyledDday = styled.span`
  color: ${({ theme }) => theme.COLOR.error};
`;

export const RoomTagBox = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
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
