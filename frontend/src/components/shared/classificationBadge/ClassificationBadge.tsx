import * as S from "@/components/shared/classificationBadge/ClassificationBadge.style";
import { Classification } from "@/@types/roomInfo";
import { classificationText } from "@/utils/roomInfoUtils";

const convertToKorean = (text: Classification) => {
  switch (text) {
    case "ALL":
      return "전체";
    case "ANDROID":
      return "안드로이드";
    case "BACKEND":
      return "백엔드";
    case "FRONTEND":
      return "프론트엔드";
  }
};

const ClassificationBadge = ({ text }: { text: Classification }) => {
  return (
    <>
      <S.StyledBadge $text={text} aria-hidden>
        {classificationText(text)}
      </S.StyledBadge>
      <S.ScreenReader>{`모집분야 ${convertToKorean(text)}`}</S.ScreenReader>
    </>
  );
};

export default ClassificationBadge;
