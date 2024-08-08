import styled from "styled-components";

export const FeedbackCardListContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100vw;
  max-width: 1200px;
`;

export const FeedbackWroteUser = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  align-items: center;

  padding-top: 4rem;
`;

export const XScrollWrapper = styled.div`
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory;

  overflow: auto hidden;
  display: flex;
  flex-direction: row;

  width: 100vw;
  max-width: 1200px;

  &::-webkit-scrollbar {
    display: none;
    width: 0;
    background: transparent;
  }
`;

export const FeedbackCardListWrapper = styled.div`
  scroll-snap-align: start;

  display: flex;
  flex-shrink: 0;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: 100vw;
  max-width: 1200px;
`;

export const EmptyCircle = styled.div`
  width: 10px;
  height: 10px;
  background: ${({ theme }) => theme.COLOR.grey1};
  border-radius: 50%;
`;
