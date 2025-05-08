import styled from "styled-components";

export const Overlay = styled.div`
  pointer-events: auto;

  position: absolute;
  top: 0;
  left: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background-color: rgb(255 255 255 / 50%);
  border-radius: 10px;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 100%;

  font: ${({ theme }) => theme.TEXT.large_bold};
`;
