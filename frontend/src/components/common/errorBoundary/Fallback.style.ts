import styled from "styled-components";

export const FallbackContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  align-items: center;
  justify-content: center;

  height: 100%;
`;

export const ErrorMessage = styled.h2`
  font: ${({ theme }) => theme.TEXT.xLarge};
`;

export const Character = styled.img`
  width: 300px;
`;
