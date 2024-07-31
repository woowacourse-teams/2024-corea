import * as S from "./AttitudeScore.style";
import React from "react";

interface AttitudeScoreProps {
  score: number;
}

const AttitudeScore = ({ score }: AttitudeScoreProps) => {
  return (
    <S.AttitudeScoreContainer>
      <S.AttitudeScoreArrow $score={score} />
      <S.AttitudeScoreGauge $score={score} />
    </S.AttitudeScoreContainer>
  );
};

export default AttitudeScore;
