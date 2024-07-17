import styled from "styled-components";

export const RoomInfoCardContainer = styled.div`
  display: flex;

  width: 100%;
  padding-left: 2rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 1rem;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);
`;

export const RoomInfoCardImg = styled.img`
  overflow: hidden;
  align-self: center;

  width: 15rem;
  height: 100%;

  object-fit: scale-down;
`;

export const RoomInfoCardContent = styled.div`
  display: flex;
  flex-direction: column;

  width: calc(100% - 15rem);
  height: 100%;
  padding: 2rem;
`;

export const RoomHeaderWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  margin-bottom: 1rem;
  padding-bottom: 1rem;

  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
`;

export const RoomTitle = styled.span`
  font: ${({ theme }) => theme.TEXT.large};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const RepositoryLink = styled.a`
  cursor: pointer;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};
  text-decoration: underline !important;
  text-underline-offset: 0.3rem;

  &:hover {
    color: ${({ theme }) => theme.COLOR.primary2};
    text-decoration: underline !important;
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
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const RoomTagBox = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
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
