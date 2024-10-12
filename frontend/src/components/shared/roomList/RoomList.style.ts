import styled from "styled-components";
import media from "@/styles/media";

export const EmptyContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;

  margin-top: 4rem;

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;

export const Character = styled.img`
  width: 70%;
  max-width: 270px;
`;

export const RoomListSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const RoomListContainer = styled.div`
  display: grid;
  gap: 2rem;
  justify-content: center;

  width: 100%;
  ${media.small`
    grid-template-columns: repeat(1, minmax(0, 1fr));
  `};
  ${media.medium`
    grid-template-columns: repeat(3, minmax(0, 1fr));
  `};
  ${media.large`
    grid-template-columns: repeat(4, minmax(0, 1fr));
  `};
  padding-bottom: 1rem;
`;
