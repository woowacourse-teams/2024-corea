import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/ranking/RankingCard/RankingCard.style";
import { RankingData } from "@/@types/ranking";
import { rank1, rank2, rank3 } from "@/assets";

interface RankingCardProps {
  rankingData: RankingData[];
}

const RankingCard = ({ rankingData }: RankingCardProps) => {
  const displayOrder = [1, 0, 2];
  const orderedData = displayOrder.map((index) => rankingData[index]);

  const rankImages: Record<number, string> = {
    1: rank1,
    2: rank2,
    3: rank3,
  };

  return (
    <S.RankingCardContainer>
      <h2>평점 좋은 리뷰어 랭킹</h2>
      <S.RankingBoardContainer>
        {orderedData?.map((data) => (
          <S.RankingBoardItem key={data.rankingNumber}>
            <Profile imgSrc={data.profileImage} size={50}></Profile>
            <a href={data.githubLink}>{data.nickname}</a>
            <p>{data.averageRating}</p>
            <S.RankingBoardBar $rankingNumber={`rank-${data.rankingNumber}`}>
              {data.rankingNumber}
            </S.RankingBoardBar>
          </S.RankingBoardItem>
        ))}
      </S.RankingBoardContainer>
      <S.RankingTableContainer>
        {rankingData?.map((data) => (
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
