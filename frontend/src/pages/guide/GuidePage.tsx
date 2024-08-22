import { Link } from "react-router-dom";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/pages/guide/GuidePage.style";

const guidePageOptions = [
  {
    title: "BACKEND 코드 가이드",
    link: "https://code-review-area.notion.site/v-1-0-b2ea7761e0e949e396c3bdd45860d270?pvs=4",
  },
  { title: "FRONTEND 코드 가이드", link: "http://code-review-area.notion.site" },
];

const GuidePage = () => {
  return (
    <S.GuidPageLayout>
      <ContentSection title="분야별 코드 작성 가이드 바로가기">
        <S.GuideContainer>
          {guidePageOptions.map((option) => (
            <Link to={option.link}>
              <S.CardContainer>
                <Icon kind="link" size="1.6rem" />
                <span>{option.title}</span>
              </S.CardContainer>
            </Link>
          ))}
        </S.GuideContainer>
      </ContentSection>
    </S.GuidPageLayout>
  );
};

export default GuidePage;
