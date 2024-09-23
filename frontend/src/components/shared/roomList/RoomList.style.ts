import styled from "styled-components";
import media from "@/styles/media";

export const EmptyContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;

  margin-top: 4rem;

  p {
    font: ${({ theme }) => theme.TEXT.large};
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
  gap: 3rem;
  justify-content: space-between;

  width: 100%;
  padding-bottom: 1rem;

  @media screen and (550px <= width) and (width <= 800px) {
    grid-template-columns: repeat(2, 1fr) !important;
  }

  ${media.small`
    grid-template-columns: repeat(1, 1fr);
    gap: 2rem;
  `}

  ${media.medium`
    grid-template-columns: repeat(3, 1fr);
    gap: 2rem;
  `}

  ${media.large`
    grid-template-columns: repeat(4, 1fr);
  `}
`;
