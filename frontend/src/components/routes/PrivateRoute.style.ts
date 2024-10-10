import styled from "styled-components";

export const PrivateRouteContainer = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  margin: 20%;

  img {
    width: 50%;
    max-width: 200px;
  }

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;
