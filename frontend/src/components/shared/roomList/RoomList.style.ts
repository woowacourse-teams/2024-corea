import styled from "styled-components";
import media from "@/styles/media";

export const RoomListSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const RoomListContainer = styled.div`
  display: grid;
  justify-content: center;
  width: 100%;

  ${media.small`
    grid-template-columns: repeat(2, 1fr);
    gap: 0.5rem;
  `}

  ${media.medium`
    grid-template-columns: repeat(3, 1fr);
    gap: 1rem;
  `}

  ${media.large`
    grid-template-columns: repeat(4, 1fr);
    gap: 2rem;
  `}
`;
