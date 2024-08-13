import { css, styled } from "styled-components";

export const RankingCardContainer = styled.div`
  cursor: default;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 360px;
  margin: 0 auto;
  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  h2 {
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.primary3};
  }
`;

export const EmptyRankingData = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;
`;

// board
export const RankingBoardContainer = styled.div`
  display: flex;
  gap: 0.2rem;
  justify-content: space-between;
`;

export const RankingBoardItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: center;
  justify-content: flex-end;

  width: 100%;

  a {
    font: ${({ theme }) => theme.TEXT.semiSmall};
  }

  p {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.grey2};
  }
`;

export const RankingBoardBar = styled.div<{ $rankingNumber: string }>`
  display: flex;
  align-items: flex-end;
  justify-content: center;

  width: 100%;
  padding: 0.3rem;

  color: ${({ theme }) => theme.COLOR.grey2};

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 3px 3px 0 0;

  ${({ $rankingNumber }) => {
    switch ($rankingNumber) {
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

  border: 1px solid ${({ theme }) => theme.COLOR.grey3};
  border-radius: 8px;
`;

export const TableItem = styled.div`
  display: flex;
  justify-content: center;

  width: 50px;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey3};

  img {
    width: 26px;
    height: 26px;
  }

  a:hover {
    text-decoration: underline;
  }
`;
