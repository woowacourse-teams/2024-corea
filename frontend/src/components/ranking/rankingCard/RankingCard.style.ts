import { css, styled } from "styled-components";

export const RankingCardContainer = styled.div`
  cursor: default;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 360px;
  height: 440px;
  margin: 0 auto;
  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  h2 {
    font: ${({ theme }) => theme.TEXT.medium_bold};
    color: ${({ theme }) => theme.COLOR.primary3};
  }
`;

export const EmptyRankingData = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

// award 시상대
export const RankingAwardContainer = styled.div`
  display: flex;
  gap: 0.2rem;
  justify-content: space-between;
`;

export const RankingAwardItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: center;
  justify-content: flex-end;

  width: 100%;

  a {
    font: ${({ theme }) => theme.TEXT.small_bold};
    color: ${({ theme }) => theme.COLOR.grey2};
  }

  a:hover {
    text-decoration: underline;
  }
`;

export const RankingAwardBar = styled.div<{ $rank: string }>`
  display: flex;
  align-items: flex-end;
  justify-content: center;

  width: 100%;
  padding: 0.3rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey2};

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 3px 3px 0 0;

  ${({ $rank }) => {
    switch ($rank) {
      case "rank-1":
        return css`
          height: 70px;
        `;
      case "rank-2":
        return css`
          height: 50px;
        `;
      case "rank-3":
        return css`
          height: 30px;
        `;
    }
  }};
`;

// table
export const RankingTableContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  width: 100%;
`;

export const RankingTableItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0.5rem 0;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
`;

export const TableItem = styled.div`
  display: flex;
  justify-content: center;

  width: 60px;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey3};
  text-overflow: ellipsis;

  img {
    width: 28px;
    height: 28px;
  }

  a {
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.grey3};
  }

  a:hover {
    text-decoration: underline;
  }
`;
