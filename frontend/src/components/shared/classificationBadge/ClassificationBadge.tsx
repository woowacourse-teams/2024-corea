import * as S from "@/components/shared/classificationBadge/ClassificationBadge.style";
import { Classification } from "@/@types/roomInfo";
import { convertClassificationToKorean } from "@/utils/convertToKorean";
import { classificationText } from "@/utils/roomInfoUtils";

const ClassificationBadge = ({ text }: { text: Classification }) => {
  return (
    <>
      <S.StyledBadge $text={text} aria-hidden>
        {classificationText(text)}
      </S.StyledBadge>
      <S.ScreenReader>{`모집분야 ${convertClassificationToKorean(text)}`}</S.ScreenReader>
    </>
  );
};

export default ClassificationBadge;
