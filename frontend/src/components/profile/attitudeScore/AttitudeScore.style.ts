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

export const AttitudeScoreText = styled.span<{ $score: number }>`
  position: absolute;
  top: -35px;
  left: ${({ $score }) => {
    if ($score === 0) return `calc(${$score}% - 0.3vw)`;
    if ($score < 10) return `calc(${$score}% - 1.2vw)`;
    if ($score < 100) return `calc(${$score}% - 1.8vw)`;
    return `calc(${$score}% - 2.2vw)`;
  }};

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grass};
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
