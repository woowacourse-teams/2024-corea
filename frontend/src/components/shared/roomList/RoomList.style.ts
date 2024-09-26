import styled from "styled-components";

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
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 3rem;
  justify-content: space-between;

  width: 100%;
  padding-bottom: 1rem;
`;
