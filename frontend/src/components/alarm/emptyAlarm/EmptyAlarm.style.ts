import styled from "styled-components";

export const EmptyContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: calc(100vh - 125px);
`;

export const GuidanceWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: fit-content;

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

export const Character = styled.img`
  width: 200px;
`;