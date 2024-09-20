import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/profile/attitudeScore/AttitudeScore.style";
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
