import { useFetchRanking } from "@/hooks/queries/useFetchRanking";
import RankingCard from "@/components/ranking/RankingCard/RankingCard";
import * as S from "@/pages/ranking/RankingPage.style";
import { RankingClassification } from "@/@types/ranking";

const rankingTypes = [
  { classification: "re", title: "평점 좋은 리뷰어 랭킹" },
  { classification: "an", title: "[Android] 코드 랭킹" },
  { classification: "be", title: "[Backend] 코드 랭킹" },
  { classification: "fe", title: "[Frontend] 코드 랭킹" },
];

const RankingPage = () => {
  const { data: rankingAllData } = useFetchRanking();

  if (!rankingAllData) return null;

  return (
    <S.RankingPageLayout>
      {rankingTypes.map(
        ({ classification, title }) =>
          rankingAllData[classification as RankingClassification] && (
            <RankingCard
              key={classification}
              title={title}
              rankingData={rankingAllData[classification as RankingClassification]}
            />
          ),
      )}
    </S.RankingPageLayout>
  );
};

export default RankingPage;
