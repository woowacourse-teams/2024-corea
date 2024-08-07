import * as S from "./AttitudeScore.style";
import React from "react";
import Icon from "@/components/common/icon/Icon";
import { theme } from "@/styles/theme";

interface AttitudeScoreProps {
  score: number;
}

const AttitudeScore = ({ score }: AttitudeScoreProps) => {
  return (
    <S.AttitudeScoreContainer>
      <S.AttitudeScoreArrow $score={score}>
        <Icon kind="arrowDown" size="24" color={theme.COLOR.grass} />
      </S.AttitudeScoreArrow>
      <S.AttitudeScoreGauge $score={score} />
    </S.AttitudeScoreContainer>
  );
};

export default AttitudeScore;
