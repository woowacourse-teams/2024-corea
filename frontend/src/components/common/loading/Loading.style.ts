import styled, { keyframes } from "styled-components";

const moveUpDown = keyframes`
    0% {
    transform: translateX(0) translateY(0) rotate(0deg);
  }
  60% {
    transform: translateX(107px) translateY(-86px) rotate(40.5deg);
  }
  100% {
    transform: translateX(107px) translateY(-86px) rotate(40.5deg);
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

  path[data-idx="1"] {
    animation: ${moveUpDown} 3s ease-in-out infinite;
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
