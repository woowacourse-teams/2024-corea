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

  const boardOrder = [1, 0, 2];
  const orderedData = boardOrder.map((index) => rankingData[index]);

  return (
    <S.RankingCardContainer>
      <h2>{title}</h2>

      <S.RankingBoardContainer>
        {orderedData.map((data) => (
          <S.RankingBoardItem key={data.rankingNumber}>
            <Profile imgSrc={data.profileImage} size={50} />
            <a href={data.githubLink}>{data.nickname}</a>
            <p>{data.averageRating}</p>
            <S.RankingBoardBar $rankingNumber={`rank-${data.rankingNumber}`}>
              {data.rankingNumber}
            </S.RankingBoardBar>
          </S.RankingBoardItem>
        ))}
      </S.RankingBoardContainer>

      <S.RankingTableContainer>
        <S.RankingTableItem>
          <S.TableItem>랭킹</S.TableItem>
          <S.TableItem>닉네임</S.TableItem>
          <S.TableItem>리뷰 개수</S.TableItem>
          <S.TableItem>평점</S.TableItem>
        </S.RankingTableItem>
        {rankingData.map((data) => (
          <S.RankingTableItem key={data.rankingNumber}>
            <S.TableItem>
              <img src={rankImages[data.rankingNumber]} alt={`Rank ${data.rankingNumber}`} />
            </S.TableItem>
            <S.TableItem>
              <a href={data.githubLink}>{data.nickname}</a>
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
