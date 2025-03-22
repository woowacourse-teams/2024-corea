import styled from "styled-components";
import { FullHeightContainer } from "@/styles/layout";

export const PrivateRouteContainer = styled.div`
  ${FullHeightContainer}
  flex-direction: column;
  gap: 2rem;

  img {
    width: 50%;
    max-width: 250px;
  }

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;
