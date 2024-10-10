import styled from "styled-components";

export const PrivateRouteContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  margin-top: 20%;

  img {
    width: 50%;
    max-width: 250px;
  }

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;
