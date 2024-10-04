import styled, { keyframes } from "styled-components";

const moveUpDown = keyframes`
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-18px);
  }
`;

export const LoadingContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  svg {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  path[data-idx="10"] {
    animation: ${moveUpDown} 1s linear infinite;
  }
`;

export const BackDrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;
  opacity: 0.4;
  background-color: ${({ theme }) => theme.COLOR.black};
`;
