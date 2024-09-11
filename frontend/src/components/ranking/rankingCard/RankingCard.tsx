import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/ranking/rankingCard/RankingCard.style";
import { RankingData } from "@/@types/ranking";
import { rank1, rank2, rank3 } from "@/assets";

interface RankingCardProps {
  title: string;
  rankingData: RankingData[];
}

const rankImages: Record<number, string> = {
  1: rank1,
  2: rank2,
  3: rank3,
};

const RankingCard = ({ title, rankingData }: RankingCardProps) => {
  if (!rankingData || rankingData.length === 0) {
    return (
      <S.RankingCardContainer>
        <h2>{title}</h2>
        <S.EmptyRankingData>아직 랭킹이 없습니다.</S.EmptyRankingData>
      </S.RankingCardContainer>
    );
  }

  // 시상대 2등, 1등, 3등 순으로
  const awardOrderedData = [rankingData[1], rankingData[0], rankingData[2]];

  return (
    <S.RankingCardContainer>
      <h2>{title}</h2>

      <S.RankingAwardContainer>
        {awardOrderedData.map((data) => (
          <S.RankingAwardItem key={data.rank}>
            <Profile
              imgSrc={data.profileImage}
              size={50}
              onClick={() => window.open(data.githubLink)}
            />
            <a href={data.githubLink} target="_blank">
              {data.nickname}
            </a>
            <S.RankingAwardBar $rank={`rank-${data.rank}`}>{data.rank}</S.RankingAwardBar>
          </S.RankingAwardItem>
        ))}
      </S.RankingAwardContainer>

      <S.RankingTableContainer>
        <S.RankingTableItem>
          <S.TableItem>랭킹</S.TableItem>
          <S.TableItem>닉네임</S.TableItem>
          <S.TableItem>리뷰 개수</S.TableItem>
          <S.TableItem>평점</S.TableItem>
        </S.RankingTableItem>
        {rankingData.map((data) => (
          <S.RankingTableItem key={data.rank}>
            <S.TableItem>
              <img src={rankImages[data.rank]} alt={`Rank ${data.rank}`} />
            </S.TableItem>
            <S.TableItem>
              <a href={data.githubLink} target="_blank">
                {data.nickname}
              </a>
            </S.TableItem>
            <S.TableItem>{data.givenReviewCount}개</S.TableItem>
            <S.TableItem>{data.averageRating}</S.TableItem>
          </S.RankingTableItem>
        ))}
      </S.RankingTableContainer>
    </S.RankingCardContainer>
  );
};

export default RankingCard;
