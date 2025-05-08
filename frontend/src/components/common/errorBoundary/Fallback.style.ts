import styled from "styled-components";
import { FullHeightContainer } from "@/styles/layout";

export const FallbackContainer = styled.div`
  ${FullHeightContainer}
  flex-direction: column;
  gap: 1.5rem;
`;

export const ErrorMessage = styled.h2`
  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const Character = styled.img`
  width: 50%;
  max-width: 250px;
`;
