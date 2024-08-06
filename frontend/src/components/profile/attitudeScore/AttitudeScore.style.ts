import styled, { keyframes } from "styled-components";

const rotate = keyframes`
  from {
    transform: rotateY(0deg);
  }
  to {
    transform: rotateY(360deg);
  }
`;

export const AttitudeScoreContainer = styled.div`
  position: relative;

  width: 100%;
  height: 24px;

  background: ${({ theme }) => theme.COLOR.lightGrass};
  border-radius: 12px;
`;

export const AttitudeScoreArrow = styled.div<{ $score: number }>`
  position: absolute;
  top: -20px;
  left: ${({ $score }) => ($score === 0 ? -1 : $score - 2)}%;
  animation: ${rotate} 4s infinite linear;
`;

export const AttitudeScoreGauge = styled.div<{ $score: number }>`
  position: relative;
  top: 0;
  left: 0;

  width: ${({ $score }) => $score}%;
  height: 24px;

  background: ${({ theme }) => theme.COLOR.grass};
  border-radius: 12px;
`;
