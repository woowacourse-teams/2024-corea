import * as S from "@/components/alarm/emptyAlarm/EmptyAlarm.style";
import { thinkingCharacter } from "@/assets";
import MESSAGES from "@/constants/message";

const EmptyAlarm = () => {
  return (
    <S.EmptyContainer>
      <S.GuidanceWrapper>
        <S.Character src={thinkingCharacter} alt="생각 중인 캐릭터" />
        <p>{MESSAGES.GUIDANCE.NO_ALARM_LIST}</p>
      </S.GuidanceWrapper>
    </S.EmptyContainer>
  );
};

export default EmptyAlarm;
