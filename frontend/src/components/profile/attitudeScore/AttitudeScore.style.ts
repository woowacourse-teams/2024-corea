import styled from "styled-components";

export const AttitudeScoreContainer = styled.div`
  position: absolute;

  width: 100%;
  height: 24px;

  background: ${({ theme }) => theme.COLOR.lightGrass};
  border-radius: 12px;
`;

export const AttitudeScoreText = styled.span<{ $score: number }>`
  position: absolute;
  top: -35px;
  left: ${({ $score }) => {
    if ($score === 0) return $score - 0.3;
    if ($score < 10) return $score - 1.3;
    if ($score < 100) return $score - 1.8;
    return $score - 2.2;
  }}%;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grass};
`;

export const AttitudeScoreArrow = styled.div<{ $score: number }>`
  position: absolute;
  top: -15px;
  left: ${({ $score }) => ($score === 0 ? -1 : $score - 2)}%;

  width: 0;
  height: 0;

  border-top: 10px solid ${({ theme }) => theme.COLOR.grass};
  border-right: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-left: 10px solid transparent;
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
